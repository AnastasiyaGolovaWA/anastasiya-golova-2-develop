package com.manager.rss.controllers;

import com.manager.rss.service.dao.RssNewsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rssNews")
public class RssNewsController {
    private RssNewsInterface RssNewsInterface;

    @Autowired
    public void setRssNewsInterface(final RssNewsInterface RssNewsInterface) {
        this.RssNewsInterface = RssNewsInterface;
    }

    /**
     * Get all
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Iterable list(final Model model) {
        Iterable list = RssNewsInterface.getAll();
        return list;
    }

    /**
     * Clear table
     */
    @RequestMapping(value = "/clear", method = RequestMethod.PUT)
    public void clear() {
        RssNewsInterface.clear();
    }
}
