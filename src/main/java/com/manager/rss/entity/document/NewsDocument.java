package com.manager.rss.entity.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "news_rss_news")
@Data
public class NewsDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "tittle")
    private String tittle;

    @Field(type = FieldType.Text, name = "link")
    private String link;

    @Field(type = FieldType.Text, name = "description")
    private String description;

    @Field(type = FieldType.Date, name = "pubDate", format = DateFormat.date)
    private Date pubDate;
}
