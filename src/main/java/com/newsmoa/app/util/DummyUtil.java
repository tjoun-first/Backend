package com.newsmoa.app.util;

import com.newsmoa.app.dto.ArticleResponse;

import java.time.LocalDate;

public class DummyUtil {

    public static ArticleResponse getDummyArticle(){
        ArticleResponse article = new ArticleResponse();
        article.setArticleId(0L);
        article.setCategory("정치");
        article.setDate(LocalDate.now());
        article.setUrl("/");
        article.setTitle("Dummy Article");
        article.setContent("Dummy Content");
        article.setSimplified("Dummy Simplified");
        article.setSummary("Dummy Summary");
        return article;
    }
}
