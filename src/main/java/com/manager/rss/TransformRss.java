package com.manager.rss;

import com.manager.rss.service.dao.NewsInterface;
import com.manager.rss.service.dao.RssFeedInterface;
import com.manager.rss.service.dao.RssNewsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;


@Service
public class TransformRss {
    private NewsInterface newsInterface;

    private RssFeedInterface rssFeedInterface;

    private RssNewsInterface rssNewsInterface;

    @Autowired
    public void setNewsInterface(final NewsInterface newsInterface, final RssFeedInterface rssFeedInterface, final RssNewsInterface rssNewsInterface) {
        this.newsInterface = newsInterface;
        this.rssFeedInterface = rssFeedInterface;
        this.rssNewsInterface = rssNewsInterface;
    }

    @Scheduled(initialDelay = 2000, fixedRate = 10000)
    @Async
    public void getNewsFromRssFeed() throws ParseException {
        newsInterface.save(rssFeedInterface.getAll());
    }
}