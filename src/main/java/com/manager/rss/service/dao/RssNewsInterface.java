package com.manager.rss.service.dao;

import com.manager.rss.entity.RssNews;

import java.util.List;

public interface RssNewsInterface {

    RssNews save(final RssNews rssNews);

    List<RssNews> getAll();

    void clear();
}
