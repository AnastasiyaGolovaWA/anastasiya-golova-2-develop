package com.manager.rss.service.dao;

import com.manager.rss.entity.News;
import com.manager.rss.entity.RssFeed;
import com.manager.rss.entity.dto.NewsDto;

import java.util.List;

public interface NewsInterface {
    List<News> getAll();

    List<NewsDto> getNews();

    void save(final List<RssFeed> rssFeed);

    void clear();

    List<News> findByTittleOrDescription(String tittle, String description);
}