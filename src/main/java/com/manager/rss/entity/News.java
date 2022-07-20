package com.manager.rss.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class News {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNews;

    @Column
    private String tittle;

    @Column
    private String link;

    @Column
    private String description;

    @Column
    private String pubDate;

    public News(final String tittle, final String link, final String description, final String pubDate) {
        this.tittle = tittle;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }
}
