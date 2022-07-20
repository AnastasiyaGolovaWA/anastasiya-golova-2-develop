package com.manager.rss.entity.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "index")
@Data
public class NewsDocument {
    @Id
    private String id;

    @Field(type = FieldType.Keyword, name = "tittle")
    private String tittle;

    @Field(type = FieldType.Text, name = "link")
    private String link;

    @Field(type = FieldType.Keyword, name = "description")
    private String description;

    @Field(type = FieldType.Date_Range, name = "pubDate", format = DateFormat.date)
    private String pubDate;
}
