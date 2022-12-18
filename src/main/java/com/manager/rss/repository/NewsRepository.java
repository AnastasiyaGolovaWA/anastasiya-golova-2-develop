package com.manager.rss.repository;

import com.manager.rss.entity.News;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
public interface NewsRepository extends JpaRepository<News, Long> {
    @Modifying
    @Query(value = "DELETE FROM news", nativeQuery = true)
    void clearTable();

    @Query(value = "select * from news where (tittle= :tittle OR :tittle='') AND (description= :description OR :description='')", nativeQuery = true)
    List<News> findByTittleOrDescription(String tittle, String description);

    @Query(value = "SELECT id_news, description, tittle, pub_date, link FROM news", nativeQuery = true)
    List<News> getNews();

    @Query(value = "select * from news where tittle ilike '%вод%'", nativeQuery = true)
    List<News> findByTittle();
}
