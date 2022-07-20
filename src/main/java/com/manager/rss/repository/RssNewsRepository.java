package com.manager.rss.repository;

import com.manager.rss.entity.RssNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RssNewsRepository extends JpaRepository<RssNews, Long> {
    @Modifying
    @Query(value = "DELETE FROM rss_news", nativeQuery = true)
    void clearTable();
}
