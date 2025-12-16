package com.newsmoa.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.dto.CommentResponse;
import com.newsmoa.app.service.ArticleService;
import com.newsmoa.app.service.CommentService;
import com.newsmoa.app.service.MypageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArticleController {
	private final ArticleService articleService;
	private final MypageService mypageService;
	private final CommentService commentService;

	@Autowired
	public ArticleController(ArticleService articleService, MypageService mypageService, CommentService commentService) {
		this.articleService = articleService;
		this.mypageService = mypageService;
		this.commentService = commentService;
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

	// 조회수 상위 5개 기사 목록 조회
	@Operation(summary = "조회수 상위 5개 기사 목록 조회", description = "조회수를 기준으로 상위 5개 기사의 목록을 반환합니다.")
	@GetMapping("/recommand")
	public ResponseEntity<List<ArticleResponse>> getTopArticles() {
		return ResponseEntity.ok(articleService.getTopArticles());
	}

	// 기사에 달린 댓글 목록 조회
	@Operation(summary = "기사 댓글 목록 조회", description = "요청한 article_id의 기사에 달린 댓글을 반환합니다")
	@GetMapping("/{article_id}/comment")
	public ResponseEntity<List<CommentResponse>> getCommentsByArticle(@PathVariable("article_id") Long article_id, @AuthenticationPrincipal UserDetails userDetails) {
		String userId = (userDetails != null) ? userDetails.getUsername() : null;
		return ResponseEntity.ok(commentService.getCommentsByArticleId(article_id, userId));
	}
	


}

