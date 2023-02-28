package com.manager.rss.service.elasticSearchService;

import com.manager.rss.entity.document.TimeDocument;

public interface TimeDocumentInterface {
    TimeDocument save(final TimeDocument newTimeDocument);
}
