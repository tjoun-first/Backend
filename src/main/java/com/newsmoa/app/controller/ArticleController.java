package com.newsmoa.app.controller;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.ArticleService;
import com.newsmoa.app.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Article>> getArticles(@PathVariable String category){
        return ResponseEntity.ok().body(articleService.findArticlesByCategory(category));
    }
    
    //기사 요약문+해석문 조회
    @GetMapping("/{article_id}")
    public ResponseEntity<Object> getArticle(@PathVariable Long article_id){
        ArticleResponse article = utils.getDummyArticle();
        article.setContent(null);
        return ResponseEntity.ok().body(List.of(article));
    }
    
    //기사 원문 조회
    @GetMapping("/{article_id}/original")
    public ResponseEntity<String> getOriginalArticle(@PathVariable Long article_id){
        ArticleResponse article = utils.getDummyArticle();
        return ResponseEntity.ok().body(article.getContent());
    }
}
