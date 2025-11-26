package com.newsmoa.app.controller;

import com.newsmoa.app.dto.ArticleResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    @Operation(summary = "마이페이지 - 스크랩한 기사 목록", description = "요청한 유저가 스크랩한 기사 리스트를 반환합니다. (세션 기반 인증)") 
    @GetMapping("/scraped")
    public ResponseEntity<List<ArticleResponse>> scraped(){
        ArticleResponse article = new ArticleResponse();
        return ResponseEntity.ok().body(List.of(article));
    }

    @Operation(summary = "마이페이지 - 최근 본 기사 목록", description = "요청한 유저가 최근에 본 기사 리스트를 반환합니다. (세션 기반 인증)")
    @GetMapping("/recent")
    public ResponseEntity<List<ArticleResponse>> recent(){
        ArticleResponse article = new ArticleResponse();
        return ResponseEntity.ok().body(List.of(article));
    }
}
