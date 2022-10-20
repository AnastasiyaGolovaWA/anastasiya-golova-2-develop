package com.manager.rss.service;

import com.google.common.collect.Lists;
import com.manager.rss.entity.document.NewsDocument;
import com.manager.rss.repository.elasticsearchRepository.NewsElasticSearchRepo;
import com.manager.rss.service.elasticSearchService.NewsElasticInterface;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class NewsElasticService implements NewsElasticInterface {
    private static final String NEWS_INDEX = "index";

    private final ElasticsearchOperations elasticsearchOperations;

    private final NewsElasticSearchRepo repository;

    @Autowired
    public NewsElasticService(final ElasticsearchOperations elasticsearchOperations, final NewsElasticSearchRepo repository) {
        super();
        this.elasticsearchOperations = elasticsearchOperations;
        this.repository = repository;
    }

    @Override
    public Page<NewsDocument> getNewsByTittle(final String tittle, final PageRequest pageRequest) {
        return repository.findByTittle(tittle, pageRequest);
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
    public List<NewsDocument> processSearchByDate(String query) {
        Date date = new Date("Thu, 20 Oct 2022 19:08:49 +0300");
        Date date1 = new Date("Thu, 20 Oct 2023 19:08:49 +0300");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(
                wildcardQuery("tittle", "*" + query + "*"));

        queryBuilder.must(rangeQuery("pubDate").from(date).to(date1));

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<NewsDocument> newsHits = elasticsearchOperations.search(searchQuery, NewsDocument.class);
        List<NewsDocument> newsMatches = new ArrayList<NewsDocument>();
        newsHits.forEach(srchHit -> {
            newsMatches.add(srchHit.getContent());
        });
        return newsMatches;
    }

    @Override
    public List<NewsDocument> processSearchByTittle(final String query) {
        QueryBuilder queryBuilder = regexpQuery("tittle", ".*" + query + ".*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<NewsDocument> searchHits =
                elasticsearchOperations.search(searchQuery,
                        NewsDocument.class,
                        IndexCoordinates.of(NEWS_INDEX));

        List<NewsDocument> newsDocuments = new ArrayList<NewsDocument>();

        searchHits.getSearchHits().forEach(searchHit -> {
            newsDocuments.add(searchHit.getContent());
        });
        return newsDocuments;
    }

    @Override
    public List<NewsDocument> processSearchByDescription(final String query) {
        QueryBuilder queryBuilder = regexpQuery("description", ".*" + query + ".*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<NewsDocument> searchHits =
                elasticsearchOperations.search(searchQuery,
                        NewsDocument.class,
                        IndexCoordinates.of(NEWS_INDEX));

        List<NewsDocument> newsDocuments = new ArrayList<NewsDocument>();

        searchHits.getSearchHits().forEach(searchHit -> {
            newsDocuments.add(searchHit.getContent());
        });
        return newsDocuments;
    }
}
