package com.manager.rss.controllers;

import com.manager.rss.entity.document.NewsDocument;
import com.manager.rss.service.elasticSearchService.NewsElasticInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class NewsElasticController {
    @Autowired
    private NewsElasticInterface newsElasticInterface;

    @ResponseBody
    @RequestMapping(value = "/news/searchByTittleOrDescription", method = RequestMethod.GET)
    public List<NewsDocument> getByTitleOrDescription(
            @RequestParam(value = "tittle", required = false) String tittle,
            @RequestParam(value = "description", required = false) String description
    ) throws IOException, SQLException {
        return newsElasticInterface.processSearchByTittleOrDescription(tittle, description);
    }


    @ResponseBody
    @RequestMapping(value = "/news/repo/{tittle}", method = RequestMethod.GET)
    public Page<NewsDocument> getByTittleRepo(@PathVariable("tittle") final String tittle) throws IOException, SQLException {
        return newsElasticInterface.getNewsByTittle(tittle, PageRequest.of(0, 20));
    }

    @ResponseBody
    @RequestMapping(value = "/tittle_time/{tittle}", method = RequestMethod.GET)
    public String[] getTittleTime(@PathVariable("tittle") final String tittle, @PathVariable("description") final String description) throws IOException, SQLException {
        long startTime = System.nanoTime();
        List<NewsDocument> news = newsElasticInterface.processSearchByTittleOrDescription(tittle, description);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        return new String[]{duration / 1000000 + "мс", "Длина массива "+String.valueOf(news.size())};
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
    public List<NewsDocument> fetchByDescription(@RequestParam(value = "q", required = false) final String query) throws IOException {
        List<NewsDocument> news = newsElasticInterface.processSearchByDescription(query);
        return news;
    }

    @GetMapping("/date")
    @ResponseBody
    public List<NewsDocument> findByDate(@RequestParam(required = false, defaultValue = "") final String date, @RequestParam(required = false, defaultValue = "") String date1) throws IOException {
        List<NewsDocument> news = newsElasticInterface.processSearchByDate(date, date1);
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
