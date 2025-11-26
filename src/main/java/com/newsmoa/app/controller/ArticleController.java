package com.newsmoa.app.controller;

import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.ArticleService;
import com.newsmoa.app.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Operation(summary = "기사 목록 조회", description = "입력한 카테고리에 해당하는 기사들의 목록(title, id 등)을 반환합니다. content, simplified, summary는 null일 수 있습니다.")
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
    @Operation(summary = "기사 세부정보 조회", description = "요청한 article_id의 기사 요약(summary) 및 해석본(simplified) 정보를 반환합니다.")
    @GetMapping("/simplified/{article_id}")
    public ResponseEntity<List<ArticleResponse>> getArticle(@PathVariable("article_id") Long article_id){
        ArticleResponse article = utils.getDummyArticle();
        article.setContent(null);
        return ResponseEntity.ok().body(List.of(article));
    }
    
    //기사 원문 조회
    @Operation(summary = "기사 원문 조회", description = "요청한 article_id의 기사 원문(content)을 문자열로 반환합니다.")
    @GetMapping("/original/{article_id}")
    public ResponseEntity<String> getOriginalArticle(@PathVariable("article_id") Long article_id){
        ArticleResponse article = utils.getDummyArticle();
        return ResponseEntity.ok().body(article.getContent());
    }
}
