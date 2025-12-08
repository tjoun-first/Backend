package com.newsmoa.app.service;

import java.util.List;
import java.util.stream.Collectors;

import com.newsmoa.app.util.AiUtil;
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
    private final AiUtil aiUtil;

    // 1. 목록 조회 
    public List<ArticleResponse> getArticlesByCategory(String category) {
        return articleRepository.findByCategory(category).stream()
                .map(ArticleResponse::new) // 생성자 참조 : article -> new ArticleResponse(article) 와 동일
                .collect(Collectors.toList());
    }
    
 // 2. 단건 조회(상세 조회) ->  ID로 바로 DTO 가져오기
    public ArticleResponse getArticleResponseById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article not found with id: " + articleId));
        
        //ai가 채우는 데이터 lazy loading
        if(article.getSimplifiedContent() == null || article.getSummaryContent() == null) {
            aiUtil.simplifyArticle(article);
            aiUtil.summarizeArticle(article);
            articleRepository.save(article);
        }
        return new ArticleResponse(article);
    }

    // 엔티티 → DTO 변환 전용 메서드
    public ArticleResponse toResponse(Article article) {
        return new ArticleResponse(article);  // 생성자 활용
    }

}

