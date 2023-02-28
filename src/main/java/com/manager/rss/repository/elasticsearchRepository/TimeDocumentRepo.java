package com.manager.rss.repository.elasticsearchRepository;

import com.manager.rss.entity.document.TimeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TimeDocumentRepo extends ElasticsearchRepository<TimeDocument, String> {

}