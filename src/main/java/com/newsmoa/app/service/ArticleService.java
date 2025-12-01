package com.newsmoa.app.service;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.repository.ArticleRepository;
import com.newsmoa.app.util.CrawlingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final List<String> categories = List.of( "경제", "과학", "사회", "세계", "문화" );

    public List<Article> findArticlesByCategory(String category) {
        return articleRepository.findByCategory(category);
    }
    
    public void refreshArticle() {
        List<Article> updated = categories.stream()
                .map(CrawlingUtil::crawlArticles)
                .flatMap(List::stream)
                .toList();
        articleRepository.saveAll(updated);
    }
}
