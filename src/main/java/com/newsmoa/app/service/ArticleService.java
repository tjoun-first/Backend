package com.newsmoa.app.service;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> findArticlesByCategory(String category) {
        return articleRepository.findByCategory(category);
    }
}
