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

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

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
    public List<NewsDocument> processSearchByDate(final String query) {
        Date date = new Date("14 Jan 2022 15:41:27 +0000");
        Date date1 = new Date("19 Jan 2022 15:41:27 +0000");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(
                multiMatchQuery(query, "tittle", "description")
                        .fuzziness(Fuzziness.AUTO).operator(Operator.AND));

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
    public List<NewsDocument> processSearch(final String query) {
        // 1. Create query on multiple fields enabling fuzzy search
        QueryBuilder queryBuilder =
                multiMatchQuery(query, "tittle", "description")
                        .fuzziness(Fuzziness.AUTO).operator(Operator.AND);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .build();

        // 2. Execute search
        SearchHits<NewsDocument> newsHits =
                elasticsearchOperations
                        .search(searchQuery, NewsDocument.class,
                                IndexCoordinates.of(NEWS_INDEX));

        // 3. Map searchHits to product list
        List<NewsDocument> newsMatches = new ArrayList<NewsDocument>();
        newsHits.forEach(srchHit -> {
            newsMatches.add(srchHit.getContent());
        });
        return newsMatches;
    }

}
