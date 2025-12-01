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

import lombok.RequiredArgsConstructor;

    @RestController
    @RequestMapping("/api/article")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequiredArgsConstructor
    public class ArticleController {

        private final ArticleService articleService;

        // 기사 목록 조회 
        @GetMapping("/{category}")
        public ResponseEntity<List<Article>> getArticles(@PathVariable("category") String category) {
            return ResponseEntity.ok(articleService.findArticlesByCategory(category));
        }

        // 기사 요약문+해석문 조회 
        @GetMapping("/simplified/{article_id}")
        public ResponseEntity<ArticleResponse> getSimplifiedArticle(@PathVariable("article_id") Long article_id) {
            ArticleResponse response = articleService.getArticleResponseById(article_id);
            return ResponseEntity.ok(response);
        }

        // 기사 원문 조회 
        @GetMapping("/original/{article_id}")
        public ResponseEntity<String> getOriginalArticle(@PathVariable("article_id") Long article_id) {
            String content = articleService.getOriginalContentById(article_id);
            return ResponseEntity.ok(content);
        }
    }

