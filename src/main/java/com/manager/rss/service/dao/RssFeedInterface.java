package com.manager.rss.service.dao;

import com.manager.rss.entity.RssFeed;

import java.util.Date;
import java.util.List;

public interface RssFeedInterface {
    RssFeed save(final RssFeed newRssFeed);

    void delete(final long id);

    List<RssFeed> getAll();

    void changeCurrentPosition(final long id);

    void changeUpdateDate(final long id, final Date date);

    RssFeed getRssFeedOne(long id);

    String findRssFeedByUrl(final long id);
}
