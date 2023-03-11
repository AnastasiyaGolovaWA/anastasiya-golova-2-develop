package com.manager.rss.service;

import com.google.common.collect.Lists;
import com.manager.rss.entity.News;
import com.manager.rss.entity.document.NewsDocument;
import com.manager.rss.entity.document.TimeDocument;
import com.manager.rss.repository.elasticsearchRepository.NewsElasticSearchRepo;
import com.manager.rss.service.dao.NewsInterface;
import com.manager.rss.service.elasticSearchService.NewsElasticInterface;
import com.manager.rss.service.elasticSearchService.TimeDocumentInterface;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

@Service
public class NewsElasticService implements NewsElasticInterface {
    private static final String NEWS_INDEX = "news_rss_news";

    private final ElasticsearchOperations elasticsearchOperations;

    private final NewsElasticSearchRepo repository;

    private final TimeDocumentInterface timeDocumentInterface;

    private final NewsInterface newsInterface;

    String csvFile = "time_elastic_title.csv";

    String csvSqlFile = "time_sql_title.csv";

    String csvFile1 = "time_elastic_description.csv";

    String csvSqlFile1 = "time_sql_description.csv";

    @Autowired
    public NewsElasticService(final ElasticsearchOperations elasticsearchOperations, final NewsElasticSearchRepo repository, final TimeDocumentInterface timeDocumentInterface, final NewsInterface newsInterface) {
        super();
        this.elasticsearchOperations = elasticsearchOperations;
        this.repository = repository;
        this.timeDocumentInterface = timeDocumentInterface;
        this.newsInterface = newsInterface;
    }

    @Override
    public Page<NewsDocument> getNewsByTittle(String tittle, PageRequest pageRequest) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("tittle", tittle);

        Page<NewsDocument> result = repository.search(queryBuilder, pageRequest);

        if (result.isEmpty()) {
            queryBuilder = QueryBuilders.queryStringQuery("*" + tittle.toLowerCase() + "* OR *" + tittle.toUpperCase() + "* OR *" + Character.toUpperCase(tittle.charAt(0)) + tittle.substring(1).toLowerCase() + "*");
            result = repository.search(queryBuilder, pageRequest);
        }
        return result;
    }


    @Override
    public NewsDocument save(final NewsDocument newNewsDocument) {
        return repository.save(newNewsDocument);
    }

    @Override
    public List<NewsDocument> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Page<NewsDocument> findAll() {
        return repository.findAll(PageRequest.of(0, 20));
    }

    @Override
    public List<NewsDocument> processSearchByDate(String date_, String date1_) throws IOException {
        LocalDate date = LocalDate.parse(date_);
        LocalDate date1 = LocalDate.parse(date1_);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(rangeQuery("pubDate").from(date).to(date1));

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        long startTime = System.nanoTime(); // сохраняем время начала выполнения запроса
        SearchHits<NewsDocument> newsHits = elasticsearchOperations.search(searchQuery, NewsDocument.class);
        long endTime = System.nanoTime(); // сохраняем время окончания выполнения запроса
        long executionTime = (endTime - startTime) / 1000000; // вычисляем время выполнения запроса в миллисекундах
        System.out.println("Execution time: " + executionTime + "ms");
        FileWriter writer = new FileWriter(csvFile, true);
        CSVWriter csvWriter = new CSVWriter(writer);
        String[] data = {String.valueOf(executionTime)};
        csvWriter.writeNext(data);
        csvWriter.close();

        TimeDocument timeDocument = new TimeDocument();
        timeDocument.setTime(executionTime);
        timeDocumentInterface.save(timeDocument);

        List<NewsDocument> newsMatches = new ArrayList<NewsDocument>();
        newsHits.forEach(srchHit -> {
            newsMatches.add(srchHit.getContent());
        });

        return newsMatches;
    }

    public String convert(String message) {
        boolean result = message.matches(".*\\p{InCyrillic}.*");
        char[] ru = {'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ъ', 'ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э', 'я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю', '.'};
        char[] en = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '"', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/'};
        StringBuilder builder = new StringBuilder();

        if (result) {
            for (int i = 0; i < message.length(); i++) {
                for (int j = 0; j < ru.length; j++) {
                    if (message.charAt(i) == ru[j]) {
                        builder.append(en[j]);
                    }
                }
            }
        } else {
            for (int i = 0; i < message.length(); i++) {
                for (int j = 0; j < en.length; j++) {
                    if (message.charAt(i) == en[j]) {
                        builder.append(ru[j]);
                    }
                }
            }
        }

        return builder.toString();
    }

    @Override
    public List<NewsDocument> processSearchByTittleOrDescription(final String tittle, String description, String date_, String date1_) throws IOException {
        BoolQueryBuilder mainBoolQuery = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(description) && !StringUtils.isEmpty(tittle)) {
            mainBoolQuery.must(QueryBuilders.multiMatchQuery(description, tittle)
                    .field("description", 2.0f)
                    .field("tittle", 1.0f)
                    .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                    .operator(Operator.AND)
                    .fuzziness(Fuzziness.AUTO)
                    .prefixLength(3)
                    .maxExpansions(10));
        } else if (!StringUtils.isEmpty(tittle)) {
            mainBoolQuery.must(QueryBuilders.matchQuery("tittle", tittle)
                    .operator(Operator.AND)
                    .fuzziness(Fuzziness.AUTO)
                    .prefixLength(3)
                    .maxExpansions(10));
            long startTime1 = System.nanoTime();
            newsInterface.findByTittle(tittle);
            long endTime1 = System.nanoTime(); // сохраняем время окончания выполнения запроса
            long executionTime1 = (endTime1 - startTime1) / 1000000; // вычисляем время выполнения запроса в миллисекундах
            writeToFile(csvSqlFile, executionTime1);
        } else if (!StringUtils.isEmpty(description)) {
            mainBoolQuery.must(QueryBuilders.matchQuery("description", description)
                    .operator(Operator.AND)
                    .fuzziness(Fuzziness.AUTO)
                    .prefixLength(3)
                    .maxExpansions(10));
            long startTime0 = System.nanoTime();
            newsInterface.findByDescriptionWithSql(description);
            long endTime0 = System.nanoTime(); // сохраняем время окончания выполнения запроса
            long executionTime1 = (endTime0 - startTime0) / 1000000; // вычисляем время выполнения запроса в миллисекундах
            writeToFile(csvSqlFile1, executionTime1);
        } else {
            mainBoolQuery.must(QueryBuilders.matchAllQuery());
        }

        if (!StringUtils.isEmpty(date_) && !StringUtils.isEmpty(date1_) && !"1970-01-01".equals(date_) && !"1970-01-01".equals(date1_)) {
            LocalDate dateFromParsed = LocalDate.parse(date_);
            LocalDate dateToParsed = LocalDate.parse(date1_);

            mainBoolQuery.filter(QueryBuilders.rangeQuery("pubDate")
                    .gte(dateFromParsed)
                    .lte(dateToParsed));
        }

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(mainBoolQuery)
                .withPageable(PageRequest.of(0, 5))
                .build();

        long startTime = System.nanoTime();
        SearchHits<NewsDocument> searchHits =
                elasticsearchOperations.search(searchQuery,
                        NewsDocument.class,
                        IndexCoordinates.of(NEWS_INDEX));
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;
        writeToFile(csvFile1, executionTime);
        System.out.println("Execution time: " + executionTime + "ms");

        List<NewsDocument> newsDocuments = new ArrayList<NewsDocument>();

        searchHits.getSearchHits().forEach(searchHit -> {
            newsDocuments.add(searchHit.getContent());
        });

        return newsDocuments;
    }

    @Override
    public List<NewsDocument> processSearchByDescription(final String query) throws IOException {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should(QueryBuilders.wildcardQuery("description", "*" + query.toLowerCase() + "*"));
        boolQuery.should(QueryBuilders.wildcardQuery("description", "*" + query.toUpperCase() + "*"));
        boolQuery.should(QueryBuilders.wildcardQuery("description", "*" + Character.toUpperCase(query.charAt(0)) + query.substring(1).toLowerCase() + "*"));
        boolQuery.should(QueryBuilders.fuzzyQuery("description", query).fuzziness(Fuzziness.AUTO));


        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(boolQuery)
                .withPageable(PageRequest.of(0, 5))
                .build();

        long startTime = System.nanoTime(); // сохраняем время начала выполнения запроса
        SearchHits<NewsDocument> searchHits =
                elasticsearchOperations.search(searchQuery,
                        NewsDocument.class,
                        IndexCoordinates.of(NEWS_INDEX));
        long endTime = System.nanoTime(); // сохраняем время окончания выполнения запроса
        long executionTime = (endTime - startTime) / 1000000; // вычисляем время выполнения запроса в миллисекундах
        System.out.println("Execution time: " + executionTime + "ms");

        TimeDocument timeDocument = new TimeDocument();
        timeDocument.setTime(executionTime);
        timeDocumentInterface.save(timeDocument);

        long startTime_ = System.nanoTime();
        List<News> news = newsInterface.findByDescriptionWithSql(query);
        long endTime_ = System.nanoTime(); // сохраняем время окончания выполнения запроса
        long duration = (endTime_ - startTime_) / 1000000;

        writeToFile(csvSqlFile1, duration);

        List<NewsDocument> newsDocuments = new ArrayList<NewsDocument>();

        searchHits.getSearchHits().forEach(searchHit -> {
            newsDocuments.add(searchHit.getContent());
        });
        writeToFile(csvFile1, executionTime);

        return newsDocuments;
    }

    public static void writeToFile(final String file, final long time) throws IOException {
        final FileWriter writer = new FileWriter(file, true);
        final CSVWriter csvWriter = new CSVWriter(writer);
        final String[] data = {String.valueOf(time)};
        csvWriter.writeNext(data);
        csvWriter.close();
    }
}


