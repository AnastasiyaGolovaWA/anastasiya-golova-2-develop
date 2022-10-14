package com.manager.rss.repository;

import com.manager.rss.entity.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE rss_feed\n" +
            "SET current_position = NOT current_position\n" +
            "WHERE id_rss = :idRss", nativeQuery = true)
    void changeOfCurrentPosition(long idRss);

    @Transactional
    @Modifying
    @Query(value = "UPDATE rss_feed\n" +
            "SET date_updated = :date_updated\n" +
            "WHERE id_rss = :idRss", nativeQuery = true)
    void changeUpdateDate(long idRss, Date date_updated);

    @Transactional
    @Query(value = "SELECT url FROM rss_feed where id_rss = :idRss", nativeQuery = true)
    String findUrlById(long idRss);
}
