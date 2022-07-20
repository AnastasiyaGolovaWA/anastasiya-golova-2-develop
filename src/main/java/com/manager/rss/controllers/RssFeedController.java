package com.manager.rss.controllers;

import com.manager.rss.entity.RssFeed;
import com.manager.rss.service.dao.RssFeedInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rssFeed")
public class RssFeedController {
    private RssFeedInterface RssFeedInterface;

    @Autowired
    public void setRssFeedInterface(final RssFeedInterface RssFeedInterface) {
        this.RssFeedInterface = RssFeedInterface;
    }

    /**
     * Add rss feed
     *
     * @param rssFeed
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addRssFeed(@RequestBody final RssFeed rssFeed) {
        RssFeedInterface.save(rssFeed);
        return new ResponseEntity("RssFeed add successfully", HttpStatus.OK);
    }

    /**
     * Delete rss feed
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable final Integer id) {
        RssFeedInterface.delete(id);
        return new ResponseEntity("Rss feed deleted successfully", HttpStatus.OK);
    }

    /**
     * Get all rss feeds
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Iterable list(final Model model) {
        Iterable list = RssFeedInterface.getAll();
        return list;
    }

    /**
     * Update current rss feed position
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/changeCurrentPosition/{id}", method = RequestMethod.PUT)
    public ResponseEntity changeCurrentRssFeedPosition(@PathVariable final Long id) {
        RssFeedInterface.changeCurrentPosition(id);
        return new ResponseEntity("Rss feed current position update successfully", HttpStatus.OK);
    }

    /**
     * Find url by Id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findUrl/{id}", method = RequestMethod.GET)
    public String findUrl(@PathVariable final Long id) {
        return RssFeedInterface.findRssFeedByUrl(id);
    }
}
