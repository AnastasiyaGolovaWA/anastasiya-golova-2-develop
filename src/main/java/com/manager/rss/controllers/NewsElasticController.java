package com.manager.rss.controllers;

import com.manager.rss.entity.document.NewsDocument;
import com.manager.rss.service.elasticSearchService.NewsElasticInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsElasticController {
    @Autowired
    private NewsElasticInterface newsElasticInterface;

    @ResponseBody
    @RequestMapping(value = "/news/{tittle}", method = RequestMethod.GET)
    public Page<NewsDocument> getByTittle(@PathVariable("tittle") final String tittle) {
        return newsElasticInterface.getNewsByTittle(tittle, PageRequest.of(0, 20));
    }

    /**
     * Get all news
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Iterable list(final Model model) {
        Iterable list = newsElasticInterface.getAll();
        return list;
    }

    /**
     * get news
     *
     * @param query
     * @return
     */
    @GetMapping("/news")
    @ResponseBody
    public List<NewsDocument> fetchByTittleOrDescription(@RequestParam(value = "q", required = false) final String query) {
        List<NewsDocument> news = newsElasticInterface.processSearch(query);
        return news;
    }

    @GetMapping("/date")
    @ResponseBody
    public List<NewsDocument> findByDate(@RequestParam(value = "q", required = false) final String query) {
        List<NewsDocument> news = newsElasticInterface.processSearchByDate(query);
        return news;
    }

    /**
     * get pages
     *
     * @return
     */
    @GetMapping("/getPages")
    @ResponseBody
    public Page<NewsDocument> getNewsByPages() {
        Page<NewsDocument> news = newsElasticInterface.findAll();
        return news;
    }
}
