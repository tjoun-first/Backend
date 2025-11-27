package com.newsmoa.app.controller;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/scraped")
    public ResponseEntity<List<ArticleResponse>> scraped(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Article> articles = mypageService.getScrapedArticlesByUserId(userId);

        List<ArticleResponse> response = articles.stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<ArticleResponse>> recent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Article> articles = mypageService.getRecentArticlesByUserId(userId);

        List<ArticleResponse> response = articles.stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }
}
