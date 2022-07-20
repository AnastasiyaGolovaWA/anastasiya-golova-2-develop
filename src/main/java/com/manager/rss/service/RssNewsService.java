package com.manager.rss.service;

import com.manager.rss.entity.RssNews;
import com.manager.rss.repository.RssNewsRepository;
import com.manager.rss.service.dao.RssNewsInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RssNewsService implements RssNewsInterface {
    @Autowired
    RssNewsRepository rssNewsRepository;

    @Override
    public RssNews save(final RssNews rssNews) {
        return rssNewsRepository.save(rssNews);
    }

    @Override
    public List<RssNews> getAll() {
        return rssNewsRepository.findAll();
    }

    @Override
    @Transactional
    public void clear() {
        rssNewsRepository.clearTable();
    }
}
