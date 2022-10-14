package com.manager.rss.service;

import com.manager.rss.entity.RssFeed;
import com.manager.rss.repository.RssFeedRepository;
import com.manager.rss.service.dao.RssFeedInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RssFeedService implements RssFeedInterface {
    @Autowired
    RssFeedRepository rssFeedRepository;

    @Override
    public RssFeed save(final RssFeed newRssFeed) {
        return rssFeedRepository.save(newRssFeed);
    }

    @Override
    public void delete(final long id) {
        rssFeedRepository.deleteById(id);
    }

    @Override
    public List<RssFeed> getAll() {
        return rssFeedRepository.findAll();
    }

    @Override
    public void changeCurrentPosition(final long id) {
        rssFeedRepository.changeOfCurrentPosition(id);
    }

    @Override
    public void changeUpdateDate(final long id, final Date date) {
        rssFeedRepository.changeUpdateDate(id, date);
    }

    @Override
    public RssFeed getRssFeedOne(final long id) {
        return rssFeedRepository.getOne(id);
    }

    @Override
    public String findRssFeedByUrl(final long id) {
        return rssFeedRepository.findUrlById(id);
    }
}
