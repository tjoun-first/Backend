package com.newsmoa.app.controller;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;


    @Operation(summary = "마이페이지 - 스크랩한 기사 목록", description = "요청한 유저가 스크랩한 기사 리스트를 반환합니다. (세션 기반 인증)")
    @GetMapping("/scraped")
    public ResponseEntity<List<ArticleResponse>> getScrapedArticles(Authentication authentication){
        String userId = authentication.getName();
        List<ArticleResponse> response = mypageService.getScrapedArticlesByUserId(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "마이페이지 - 기사 스크랩", description = "요청한 기사를 유저의 스크랩 목록에 추가합니다.")
    @PutMapping("/scraped")
    public ResponseEntity<Void> putScrapedArticle(Authentication authentication, @RequestParam("article_id") Long articleId) {
        String userId = authentication.getName();
        mypageService.saveScrapedArticle(userId, articleId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "마이페이지 - 스크랩 기사 삭제", description = "요청한 기사를 유저의 스크랩 목록에서 삭제합니다.")
    @DeleteMapping("/scraped")
    public ResponseEntity<Void> deleteScrapedArticle(Authentication authentication, @RequestParam("article_id") Long articleId) {
        String userId = authentication.getName();
        mypageService.deleteScrapedArticle(userId, articleId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "마이페이지 - 최근 본 기사 목록", description = "요청한 유저가 최근에 본 기사 리스트를 반환합니다. (세션 기반 인증)")
    @GetMapping("/recent")
    public ResponseEntity<List<ArticleResponse>> recent(Authentication authentication){
        String userId = authentication.getName();
        List<ArticleResponse> response = mypageService.getRecentArticlesByUserId(userId);
        return ResponseEntity.ok().body(response);
    }
}

