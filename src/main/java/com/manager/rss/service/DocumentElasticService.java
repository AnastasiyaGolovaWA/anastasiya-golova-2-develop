package com.manager.rss.service;

import com.manager.rss.entity.document.TimeDocument;
import com.manager.rss.repository.elasticsearchRepository.TimeDocumentRepo;
import com.manager.rss.service.elasticSearchService.TimeDocumentInterface;
import org.springframework.stereotype.Service;

@Service
public class DocumentElasticService implements TimeDocumentInterface {
    private final TimeDocumentRepo repository;

    private static final String TIME_INDEX = "time";

    public DocumentElasticService(final TimeDocumentRepo repository) {
        this.repository = repository;
    }

    @Override
    public TimeDocument save(final TimeDocument newTimeDocument) {
        return repository.save(newTimeDocument);
    }
}
