package com.manager.rss.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
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

import java.io.IOException;
import java.text.ParseException;
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

    private static ExtentHtmlReporter htmlReporter;
    private static ExtentReports extentReports;

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();

        // Настройка ExtentReports и ExtentHtmlReporter
        htmlReporter = new ExtentHtmlReporter("test-results.html");
        htmlReporter.config().setDocumentTitle("Test Report");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(Theme.DARK);
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    @BeforeEach
    void testIsContainerRunning() {
        assertTrue(elasticsearchContainer.isRunning());
    }

    @Test
    void testNewsDocument() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("testNewsDocument", "Test search by title or description");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription("smart", null, null, null);
        assertEquals(3, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();

        // Закрытие отчета
        extentReports.flush();
    }
}
