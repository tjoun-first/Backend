package com.newsmoa.app.controller;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {


    private final Utils utils;

    public ArticleController(Utils utils) {
        this.utils = utils;
    }

    //기사 목록 조회
    @GetMapping("/{category}")
    public ResponseEntity<ArticleResponse> getArticles(@PathVariable String category){
        ArticleResponse article = utils.getDummyArticle();
        article.setContent(null);
        article.setSimplified(null);
        article.setSummary(null);
        return ResponseEntity.ok().body(article);
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
