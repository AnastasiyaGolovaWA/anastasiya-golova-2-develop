package com.manager.rss.controllers;

import com.manager.rss.entity.News;
import com.manager.rss.entity.RssFeed;
import com.manager.rss.service.dao.NewsInterface;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/news")
public class NewsController {
    private NewsInterface newsInterface;

    String csvSqlFile = "time_sql_title.csv";

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
    public ResponseEntity addNews(@RequestBody final List<RssFeed> rssFeed) throws ParseException {
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
    public List<News> findByTittleOrDescription(@RequestParam(required = false, defaultValue = "") final String tittle, @RequestParam(required = false, defaultValue = "") String description) throws IOException {
        long startTime = System.nanoTime();
        List<News> news = newsInterface.findByTittleOrDescription(tittle, description);
        long endTime = System.nanoTime(); // сохраняем время окончания выполнения запроса
        long duration = (endTime - startTime) / 1000000;
        FileWriter writer = new FileWriter(csvSqlFile, true);
        CSVWriter csvWriter = new CSVWriter(writer);
        String[] data = {String.valueOf(duration)};
        csvWriter.writeNext(data);
        csvWriter.close();
        return news;
    }

    /**
     * Find news by tittle
     *
     * @return
     */
    @RequestMapping(value = "/findByTittleWithSql", method = RequestMethod.GET)
    public String[] findByTittleWithSql(@RequestParam(required = false, defaultValue = "") final String tittle) throws IOException {
        long startTime = System.nanoTime();
        //List<News> news = newsInterface.findByTittleWithSql(tittle);
        List<News> news = null;
        long endTime = System.nanoTime(); // сохраняем время окончания выполнения запроса
        long duration = (endTime - startTime) / 1000000;
        FileWriter writer = new FileWriter(csvSqlFile, true);
        CSVWriter csvWriter = new CSVWriter(writer);
        String[] data = {String.valueOf(duration)};
        csvWriter.writeNext(data);
        csvWriter.close();
        return new String[]{duration / 1000000 + "мс", "Длина массива "+String.valueOf(news.size())};
    }

    /**
     * Find news by description
     *
     * @return
     */
    @RequestMapping(value = "/findByDescriptionWithSql", method = RequestMethod.GET)
    public String[] findByDescriptionWithSql(@RequestParam(required = false, defaultValue = "") final String description) throws IOException {
        long startTime = System.nanoTime();
        List<News> news = newsInterface.findByDescriptionWithSql(description);
        long endTime = System.nanoTime(); // сохраняем время окончания выполнения запроса
        long duration = (endTime - startTime) / 1000000;
        FileWriter writer = new FileWriter(csvSqlFile, true);
        CSVWriter csvWriter = new CSVWriter(writer);
        String[] data = {String.valueOf(duration)};
        csvWriter.writeNext(data);
        csvWriter.close();
        return new String[]{duration / 1000000 + "мс", "Длина массива "+String.valueOf(news.size())};
    }


    /**
     * Clear table
     */
    @RequestMapping(value = "/clear", method = RequestMethod.PUT)
    public void clear() {
        newsInterface.clear();
    }
}
