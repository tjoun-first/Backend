package com.newsmoa.app.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleResponse {
    private Long articleId;
    private String category;
    private LocalDate date;
    private String url;
    private String title;
    private String content;
    private String simplified;
    private String summary;
}
