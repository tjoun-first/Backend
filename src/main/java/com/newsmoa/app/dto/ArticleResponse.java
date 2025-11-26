package com.newsmoa.app.dto;

import com.newsmoa.app.domain.Article;
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
    
    public static ArticleResponse from(Article article){
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setArticleId(article.getArticleId());
        articleResponse.setCategory(article.getCategory());
        articleResponse.setDate(article.getDate());
        articleResponse.setUrl(article.getUrl());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        articleResponse.setSimplified(article.getSimplifiedContent());
        articleResponse.setSummary(article.getSummaryContent());
        return articleResponse;
    }
}
