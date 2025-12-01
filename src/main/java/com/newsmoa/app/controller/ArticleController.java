package com.newsmoa.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<List<Article>> getArticles(@PathVariable("category") String category) {
        return ResponseEntity.ok(articleService.findArticlesByCategory(category));
    }
    
    //기사 요약문+해석문 조회
    @Operation(summary = "기사 세부정보 조회", description = "요청한 article_id의 기사 요약(summary) 및 해석본(simplified) 정보를 반환합니다.")
    @GetMapping("/simplified/{article_id}")
    public ResponseEntity<ArticleResponse> getSimplifiedArticle(@PathVariable("article_id") Long article_id) {
        ArticleResponse response = articleService.getArticleResponseById(article_id);
        return ResponseEntity.ok(response);
    }
    
    //기사 원문 조회
    @Operation(summary = "기사 원문 조회", description = "요청한 article_id의 기사 원문(content)을 문자열로 반환합니다.")
    @GetMapping("/original/{article_id}")
    public ResponseEntity<String> getOriginalArticle(@PathVariable("article_id") Long article_id) {
        String content = articleService.getOriginalContentById(article_id);
        return ResponseEntity.ok(content);
    }
}
