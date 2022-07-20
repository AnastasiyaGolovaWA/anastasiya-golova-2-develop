package com.manager.rss.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rss_news")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RssNews {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRssNews;

    @ManyToOne
    @JoinColumn(name = "id_news")
    private News news;

    @ManyToOne
    @JoinColumn(name = "id_rss")
    private RssFeed rssFeed;
}
