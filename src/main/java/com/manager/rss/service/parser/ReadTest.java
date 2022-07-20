package com.manager.rss.service.parser;

import com.manager.rss.entity.News;

import java.util.Date;
import java.util.List;

public class ReadTest {
    public static void main(String[] args) {
        RssFeedParser parser = new RssFeedParser(
                "https://lenta.ru/rss");
        List<News> feed = parser.readNews();
        Date todayDate = new Date("13 Jan 2022 15:41:27 +0000");
        Date historyDate = new Date("12 Jan 2022 15:41:27 +0000");
        Date futureDate = new Date("Thu, 14 Jan 2022 19:43:28 +0300");
        if (todayDate.after(historyDate) && todayDate.before(futureDate)) {
            System.out.println("TEST");
        }
    }
}