package com.manager.rss.service;

import com.manager.rss.entity.News;
import com.manager.rss.entity.RssFeed;
import com.manager.rss.entity.RssNews;
import com.manager.rss.entity.document.NewsDocument;
import com.manager.rss.entity.dto.NewsDto;
import com.manager.rss.entity.mapper.NewsMapper;
import com.manager.rss.repository.NewsRepository;
import com.manager.rss.service.dao.NewsInterface;
import com.manager.rss.service.elasticSearchService.NewsElasticInterface;
import com.manager.rss.service.parser.RssFeedParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService implements NewsInterface {
    @Autowired
    NewsRepository newsRepository;

    private RssNewsService rssNewsService;

    private NewsElasticInterface newsElasticInterface;

    private final NewsMapper newsMapper;

    @Autowired
    public void setRssNewsService(final RssNewsService rssNewsService) {
        this.rssNewsService = rssNewsService;
    }

    @Autowired
    public void setNewsElasticInterface(final NewsElasticInterface newsElasticInterface) {
        this.newsElasticInterface = newsElasticInterface;
    }

    @Override
    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @Override
    public List<NewsDto> getNews() {
        return newsRepository.findAll().stream()
                .map(newsMapper::mapToNewsDto).collect(Collectors.toList());
    }

    @Override
    public List<News> findByTittleOrDescription(final String tittle, final String description) {
        return newsRepository.findByTittleOrDescription(tittle, description);
    }

    @Override
    public void save(final List<RssFeed> rssFeed) {
        for (int k = 0; k < rssFeed.size(); k++) {
            final RssFeedParser parser = new RssFeedParser(rssFeed.get(k).getUrl());
            final List<News> feed = parser.readNews();
            for (int i = 0; i < feed.size(); i++) {
                if (!existNews(feed.get(i))) {
                    newsRepository.save(feed.get(i));
                    final RssNews rssNews = new RssNews();
                    rssNews.setNews(feed.get(i));
                    rssNews.setRssFeed(rssFeed.get(k));
                    rssNewsService.save(rssNews);
                    NewsDocument newsDocument = new NewsDocument();
                    newsDocument.setDescription(feed.get(i).getDescription());
                    newsDocument.setLink(feed.get(i).getLink());
                    newsDocument.setTittle(feed.get(i).getTittle());
                    newsDocument.setPubDate(feed.get(i).getPubDate());
                    newsElasticInterface.save(newsDocument);
                } else {
                    System.out.println("ERROR");
                }
            }
        }
    }

    private boolean existNews(final News a) {
        for (final News s : getAll()) {
            if (a.getLink().equals(s.getLink())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void clear() {
        newsRepository.clearTable();
    }
}