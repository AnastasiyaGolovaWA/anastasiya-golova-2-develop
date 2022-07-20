package com.manager.rss.repository.elasticsearchRepository;

import com.manager.rss.entity.document.NewsDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface NewsElasticSearchRepo extends ElasticsearchRepository<NewsDocument, String> {

    List<NewsDocument> findByTittleAndDescription(String tittle, String description);

    Page<NewsDocument> findByTittle(String tittle, Pageable pageable);
}