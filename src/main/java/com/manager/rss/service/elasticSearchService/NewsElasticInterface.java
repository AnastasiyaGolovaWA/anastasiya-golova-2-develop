package com.manager.rss.service.elasticSearchService;

import com.manager.rss.entity.document.NewsDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface NewsElasticInterface {

    Page<NewsDocument> getNewsByTittle(String tittle, PageRequest pageRequest);

    NewsDocument save(final NewsDocument newNewsDocument);

    List<NewsDocument> getAll();

    Page<NewsDocument> findAll();

    List<NewsDocument> processSearchByDate(String date, String date1) throws IOException;

    List<NewsDocument> processSearchByTittle(final String query) throws IOException;

    List<NewsDocument> processSearchByDescription(final String query) throws IOException;
}
