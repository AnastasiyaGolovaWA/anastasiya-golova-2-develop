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
    void testNewsDocumentByTitle() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по заголовку", "Поиск по заголовку содержащему 'smart'");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription("smart", null, null, null);
        assertEquals(3, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @Test
    void testNewsDocumentByTitleWithPhrase() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по заголовку содержащему фразу", "Поиск по заголовку содержащему фразу 'Ученые составили полную карту мозга дрозофилы'");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription("Ученые составили полную карту мозга дрозофилы", null, null, null);
        assertEquals(1, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @Test
    void testNewsDocumentByTittleWithMisspell() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по заголовку с опечаткой", "Поиск по заголовку содержащему 'smartt'");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription("smartt", null, null, null);
        assertEquals(3, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @Test
    void testNewsDocumentByDescription() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по описанию", "Поиск по описанию содержащему 'small'");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription(null, "small", null, null);
        assertEquals(1, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @Test
    void testNewsDocumentByDescriptionWithMisspell() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по описанию с опечаткой", "Поиск по описанию содержащему 'smallt'");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription(null, "smalll", null, null);
        assertEquals(1, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @Test
    void testNewsDocumentByDateRange() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по диапазону дат", "Поиск по датам с 3 марта 2023 по 7 марта 2023");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription(null, null, "2023-03-03", "2023-03-07");
        assertEquals(5, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @Test
    void testNewsDocumentByDateRangeAndTittle() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по диапазону дат и заголовку", "Поиск по датам с 3 марта 2023 по 7 марта 2023 и заголовку содержащему 'smart");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription("smart", null, "2023-03-03", "2023-03-07");
        assertEquals(0, result.size());

        // Добавление шага в тест
        test.pass("Test passed");
    }

    @Test
    void testNewsDocumentByDateRangeAndDescription() throws IOException, ParseException {
        // Создание теста в отчете
        ExtentTest test = extentReports.createTest("Поиск по диапазону дат и описанию", "Поиск по датам с 20 февраля 2023 по 23 февраля 2023 и описанию содержащему 'small");

        List<NewsDocument> result = newsElasticService.processSearchByTittleOrDescription(null, "small", "2023-02-20", "2023-02-23");
        assertEquals(1, result.size());

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
