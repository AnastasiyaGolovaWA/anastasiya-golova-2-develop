package com.manager.rss.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdated;
}
