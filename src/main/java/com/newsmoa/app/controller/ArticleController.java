package com.newsmoa.app.controller;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.ArticleService;
import com.newsmoa.app.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class ArticleController {
    private final ArticleService articleService;
    private final Utils utils;
    
    @Autowired
    public ArticleController(Utils utils, ArticleService articleService) {
        this.utils = utils;
        this.articleService = articleService;
    }

    //기사 목록 조회
    @GetMapping("/{category}")
    public ResponseEntity<List<ArticleResponse>> getArticles(@PathVariable("category") String category){
        List<ArticleResponse> responses = articleService
                .findArticlesByCategory(category)
                .stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }
    
    //기사 요약문+해석문 조회
    @GetMapping("/simpilfied/{article_id}")
    public ResponseEntity<List<ArticleResponse>> getArticle(@PathVariable("article_id") Long article_id){
        ArticleResponse article = utils.getDummyArticle();
        article.setContent(null);
        return ResponseEntity.ok().body(List.of(article));
    }
    
    //기사 원문 조회
    @GetMapping("/original/{article_id}")
    public ResponseEntity<String> getOriginalArticle(@PathVariable("article_id") Long article_id){
        ArticleResponse article = utils.getDummyArticle();
        return ResponseEntity.ok().body(article.getContent());
    }
}
