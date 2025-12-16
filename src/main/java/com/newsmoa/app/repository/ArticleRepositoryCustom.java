package com.newsmoa.app.repository;

import com.newsmoa.app.domain.Article;

import java.util.List;

public interface ArticleRepositoryCustom {
    void saveAllArticles(List<Article> articles);
}
