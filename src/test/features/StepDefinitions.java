package features;

import com.manager.rss.entity.document.NewsDocument;
import com.manager.rss.service.NewsElasticService;
import com.manager.rss.service.NewsService;
import com.manager.rss.test.NewsElasticsearchContainer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

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

    private String articleTitle;

    @Given("a news article with title {string}")
    public void givenANewsArticleWithTitle(String title) {
        // implementation to create a news article with provided title
        articleTitle = title;
    }

    @When("I search for news with title {string}")
    public void whenISearchForNewsWithTitle(String title) throws IOException, ParseException {
        // implementation to search for news article with provided title
        List<NewsDocument> newsDocuments = newsElasticService.processSearchByTittleOrDescription(title, null, null, null);
        System.out.println(newsDocuments);
    }

    @Then("I should see a news article with title {string}")
    public void thenIShouldSeeANewsArticleWithTitle(String title) {
        // implementation to verify that the news article with provided title is displayed
    }
}
