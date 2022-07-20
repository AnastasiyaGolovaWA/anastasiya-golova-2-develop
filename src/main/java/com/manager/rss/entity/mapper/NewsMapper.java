package com.manager.rss.entity.mapper;

import com.manager.rss.entity.News;
import com.manager.rss.entity.dto.NewsDto;
import org.springframework.stereotype.Service;

@Service
public class NewsMapper {
    public NewsDto mapToNewsDto(News entity) {
        NewsDto dto = new NewsDto();
        dto.setLink(entity.getLink());
        dto.setDescription(entity.getDescription());
        dto.setTittle(entity.getTittle());
        return dto;
    }

    public News mapToNewsEntity(NewsDto dto) {
        News entity = new News();
        entity.setLink(dto.getLink());
        entity.setTittle(dto.getTittle());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}