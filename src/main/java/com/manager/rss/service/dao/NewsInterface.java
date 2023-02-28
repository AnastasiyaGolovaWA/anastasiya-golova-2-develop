package com.manager.rss.service.dao;

import com.manager.rss.entity.News;
import com.manager.rss.entity.RssFeed;
import com.manager.rss.entity.dto.NewsDto;

import java.text.ParseException;
import java.util.List;

public interface NewsInterface {
    List<News> getAll();

    List<NewsDto> getNews();

    List<News> findByTittleWithSql(String tittle);

    List<News> findByDescriptionWithSql(String description);

    void save(final List<RssFeed> rssFeed) throws ParseException;

    void clear();

    List<News> findByTittleOrDescription(String tittle, String description);
}
