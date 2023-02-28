package com.manager.rss.entity.document;
import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "time")
@Data
public class TimeDocument {

    @Id
    private String id;

    @Field(type = FieldType.Long, name = "time")
    private Long time;
}
