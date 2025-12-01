package com.newsmoa.app.controller;

import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class ArticleController {
    private final ArticleService articleService;
    
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //기사 목록 조회
    @Operation(summary = "기사 목록 조회", description = "입력한 카테고리에 해당하는 기사들의 목록(title, id 등)을 반환합니다. content, simplified, summary는 null일 수 있습니다.")
    @GetMapping("/{category}")
    public ResponseEntity<List<Article>> getArticles(@PathVariable("category") String category){
        return ResponseEntity.ok().body(articleService.findArticlesByCategory(category));
    }
    
    //기사 요약문+해석문 조회
    @Operation(summary = "기사 세부정보 조회", description = "요청한 article_id의 기사 요약(summary) 및 해석본(simplified) 정보를 반환합니다.")
    @GetMapping("/simplified/{article_id}")
    public ResponseEntity<ArticleResponse> getSimplifiedArticle(@PathVariable("article_id") Long article_id){
        Article article = articleService.findArticleById(article_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found with id: " + article_id));

        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setArticleId(article.getArticleId());
        articleResponse.setCategory(article.getCategory());
        articleResponse.setDate(article.getDate());
        articleResponse.setUrl(article.getUrl());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        articleResponse.setSimplified(article.getSimplifiedContent());
        articleResponse.setSummary(article.getSummaryContent());
        
        return ResponseEntity.ok().body(articleResponse);
    }
    
    //기사 원문 조회
    @Operation(summary = "기사 원문 조회", description = "요청한 article_id의 기사 원문(content)을 문자열로 반환합니다.")
    @GetMapping("/original/{article_id}")
    public ResponseEntity<String> getOriginalArticle(@PathVariable("article_id") Long article_id){
        Article article = articleService.findArticleById(article_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found with id: " + article_id));
        return ResponseEntity.ok().body(article.getContent());
    }
}
