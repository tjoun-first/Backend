package com.newsmoa.app.dto;

import java.time.LocalDateTime;

import com.newsmoa.app.domain.YourArticle;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecentArticleResponse {
	private Long articleId;
    private String title;
    private String category;
    private LocalDateTime viewedAt;

    public RecentArticleResponse(YourArticle yourArticle) {
    	this.articleId = yourArticle.getArticle().getArticleId();
        this.title = yourArticle.getArticle().getTitle();
        this.category =yourArticle.getArticle().getCategory();
        this.viewedAt = yourArticle.getViewedAt();
    }
}
