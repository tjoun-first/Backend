package com.newsmoa.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.repository.ArticleRepository;
import com.newsmoa.app.util.CrawlingUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final List<String> categories = List.of( "경제", "과학", "사회", "세계", "문화" );

    // 기존 메서드
    public List<Article> findArticlesByCategory(String category) {
        return articleRepository.findByCategory(category);
    }

    public java.util.Optional<Article> findArticleById(Long articleId) {
        return articleRepository.findById(articleId);
    }
    
    public void refreshArticle() {
        List<Article> updated = categories.stream()
                .map(CrawlingUtil::crawlArticles)
                .flatMap(List::stream)
                .toList();
        articleRepository.saveAll(updated);
    }

    // 엔티티 → DTO 변환 전용 메서드
    public ArticleResponse toResponse(Article article) {
        return new ArticleResponse(article);  // 생성자 활용
    }

    // ID로 바로 DTO 가져오기
    public ArticleResponse getArticleResponseById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article not found with id: " + articleId));
        return new ArticleResponse(article);
    }

}

