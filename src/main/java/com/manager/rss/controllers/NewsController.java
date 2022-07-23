package com.manager.rss.controllers;

import com.manager.rss.entity.News;
import com.manager.rss.entity.RssFeed;
import com.manager.rss.service.dao.NewsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/news")
public class NewsController {
    private NewsInterface newsInterface;

    @Autowired
    public void setNewsInterface(final NewsInterface newsInterface) {
        this.newsInterface = newsInterface;
    }

    /**
     * Add news
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addNews(@RequestBody final List<RssFeed> rssFeed) {
        newsInterface.save(rssFeed);
        return new ResponseEntity("News add successfully", HttpStatus.OK);
    }

    /**
     * Get all news
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Iterable list(final Model model) {
        Iterable list = newsInterface.getAll();
        return list;
    }

    /**
     * Get news
     */
    @RequestMapping(value = "/getNews", method = RequestMethod.GET)
    public Iterable getNews(final Model model) {
        Iterable list = newsInterface.getNews();
        return list;
    }

    /**
     * Find news by tittle and description
     *
     * @param tittle
     * @param description
     * @return
     */
    @RequestMapping(value = "/showByParametres", method = RequestMethod.GET)
    public List<News> findByTittleOrDescription(@RequestParam(required = false, defaultValue = "") final String tittle, @RequestParam(required = false, defaultValue = "") String description) {
        List<News> news = newsInterface.findByTittleOrDescription(tittle, description);
        return news;
    }


    /**
     * Clear table
     */
    @RequestMapping(value = "/clear", method = RequestMethod.PUT)
    public void clear() {
        newsInterface.clear();
    }
}
