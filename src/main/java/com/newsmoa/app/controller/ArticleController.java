package com.newsmoa.app.controller;

import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.service.ArticleService;
import com.newsmoa.app.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArticleController {
	private final ArticleService articleService;
	private final MypageService mypageService;

	@Autowired
	public ArticleController(ArticleService articleService, MypageService mypageService) {
		this.articleService = articleService;
		this.mypageService = mypageService;
	}

	// 기사 목록 조회
	@Operation(summary = "기사 목록 조회", description = "입력한 카테고리에 해당하는 기사들의 목록(title, id 등)을 반환합니다. content, simplified, summary는 null일 수 있습니다.")
	@GetMapping("/{category}")
	public ResponseEntity<List<ArticleResponse>> getArticles(@PathVariable("category") String category) {
		return ResponseEntity.ok(articleService.getArticlesByCategory(category));
	}

	// 기사 세부정보 조회
	@Operation(summary = "기사 세부정보 조회", description = "요청한 article_id의 기사의 모든 세부 정보를 반환합니다.")
	@GetMapping("/id/{article_id}")
	public ResponseEntity<ArticleResponse> getArticle(@PathVariable("article_id") Long article_id, @AuthenticationPrincipal UserDetails userDetails) {
        ArticleResponse response = articleService.getArticleResponseById(article_id);
        
		if (userDetails != null) {
			String userId = userDetails.getUsername();
			mypageService.saveRecentArticle(userId, article_id);
            response.setIsScraped(mypageService.checkArticleScrapedBy(article_id, userId));
		}
		return ResponseEntity.ok(response);
	}

	// 검색어로 기사 검색
	@Operation(summary = "검색어로 기사 검색", description = "입력한 키워드를 포함하는 제목을 가진 기사들의 목록을 반환합니다.")
	@GetMapping("/search")
	public ResponseEntity<List<ArticleResponse>> searchArticles(@RequestParam("keyword") String keyword) {
        List<ArticleResponse> result = articleService.searchArticlesByKeyword(keyword);
        log.info("검색 요청됨. keyword={}, result={}", keyword, result);
        return ResponseEntity.ok(result);
	}


}
