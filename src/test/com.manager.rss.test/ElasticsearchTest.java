package com.manager.rss.test;

import com.manager.rss.entity.document.NewsDocument;
import com.manager.rss.service.NewsElasticService;
import com.manager.rss.service.NewsService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElasticsearchTest {

    @Autowired
    private NewsElasticService newsElasticService;

    @Autowired
    private NewsService newsService;

    @Container
    private static ElasticsearchContainer elasticsearchContainer = new NewsElasticsearchContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @BeforeEach
    void testIsContainerRunning() {
        assertTrue(elasticsearchContainer.isRunning());
    }

    @Test
    void testListNewsDocument() {
        List<NewsDocument> result = newsElasticService.getAll();
        List<NewsDocument> result1 = new ArrayList<>();
        assertEquals(result1, result);
    }

    @Test
    void testNewsDocument() {
        Page<NewsDocument> result = newsElasticService.getNewsByTittle("Телефонные мошенники начали обманывать россиян с помощью «Госуслуг»", PageRequest.of(0, 20));
        assertEquals(0, result.getTotalElements());
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }
}