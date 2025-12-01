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


    @Operation(summary = "마이페이지 - 스크랩한 기사 목록", description = "요청한 유저가 스크랩한 기사 리스트를 반환합니다. (세션 기반 인증)") 
    @GetMapping("/scraped")
    public ResponseEntity<List<ArticleResponse>> scraped(Authentication authentication){
        String userId = authentication.getName();

        List<Article> articles = mypageService.getScrapedArticlesByUserId(userId);

        List<ArticleResponse> response = articles.stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "마이페이지 - 최근 본 기사 목록", description = "요청한 유저가 최근에 본 기사 리스트를 반환합니다. (세션 기반 인증)")
    @GetMapping("/recent")
    public ResponseEntity<List<ArticleResponse>> recent(Authentication authentication){
        String userId = authentication.getName();

        List<Article> articles = mypageService.getRecentArticlesByUserId(userId);

        List<ArticleResponse> response = articles.stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }
}
