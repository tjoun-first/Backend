package com.newsmoa.app.controller;

import com.newsmoa.app.dto.ArticleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {
    
    @GetMapping("/scraped")
    public ResponseEntity<List<ArticleResponse>> scraped(){
        ArticleResponse article = new ArticleResponse();
        return ResponseEntity.ok().body(List.of(article));
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<ArticleResponse>> recent(){
        ArticleResponse article = new ArticleResponse();
        return ResponseEntity.ok().body(List.of(article));
    }
}
