package com.manager.rss.service.elasticSearchService;

import com.manager.rss.entity.document.NewsDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsElasticInterface {

    Page<NewsDocument> getNewsByTittle(String tittle, PageRequest pageRequest);

    NewsDocument save(final NewsDocument newNewsDocument);

    List<NewsDocument> getAll();

    List<NewsDocument> processSearch(final String query);

    Page<NewsDocument> findAll();

    List<NewsDocument> processSearchByDate(final String query);
}
