package com.manager.rss.repository;

import com.manager.rss.entity.News;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
public interface NewsRepository extends JpaRepository<News, Long> {
    @Modifying
    @Query(value = "DELETE FROM news", nativeQuery = true)
    void clearTable();

    @Query(value = "select * from news where (tittle= :tittle OR :tittle='') AND (description= :description OR :description='')", nativeQuery = true)
    List<News> findByTittleOrDescription(String tittle, String description);

    @Query(value = "SELECT id_news, description, tittle, pub_date, link FROM news", nativeQuery = true)
    List<News> getNews();

    @Query(value = "SELECT * " +
            "FROM news " +
            "WHERE to_tsvector('russian', tittle || ' ' || description || ' ' || pub_date) @@ " +
            "    plainto_tsquery('russian', :title || ' & ' || :description) " +
            "AND pub_date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    List<News> findByTitleAndDescriptionAndPubDateBetween(@Param("title") String title,
                                                          @Param("description") String description,
                                                          @Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT * FROM news WHERE description ILIKE %:description%", nativeQuery = true)
    List<News> findByDescription(@Param("description") String description);
}
