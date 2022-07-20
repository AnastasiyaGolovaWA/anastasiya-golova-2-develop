package com.manager.rss.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rss_feed")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RssFeed {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRss;

    @Column
    private boolean currentPosition;

    @Column
    private String url;

    @Column
    private Date dateCreated;

    @Column
    private Date dateUpdated;
}
