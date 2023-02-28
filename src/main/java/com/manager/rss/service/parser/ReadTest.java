package com.manager.rss.service.parser;

import com.manager.rss.entity.mapper.NewsMapper;
import com.manager.rss.service.NewsService;

public class ReadTest extends NewsService {

    public ReadTest(NewsMapper newsMapper) {
        super(newsMapper);
    }

    public static void main(String[] args) {
       // new NewsService(new NewsMapper()).findByTittleWithSql();
    }
}