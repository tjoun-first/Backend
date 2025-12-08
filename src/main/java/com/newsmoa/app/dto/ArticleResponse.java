package com.newsmoa.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.domain.YourArticle;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class ArticleResponse {
	private Long articleId;
	private String category;
	private LocalDate date;
	private String url;
	private String title;
	private String content;
	private String simplified;
	private String summary;
	private LocalDateTime viewedAt;

	// Article 객체를 받아서 DTO로 변환하는 생성자
    public ArticleResponse(Article article) {
        this.articleId = article.getArticleId();
        this.category = article.getCategory();
        this.date = article.getDate();
        this.url = article.getUrl();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.simplified = article.getSimplifiedContent();
        this.summary = article.getSummaryContent();
    }

	// YourArticle 객체를 받아서 DTO로 변환하는 생성자
	public ArticleResponse(YourArticle yourArticle) {
		Article article = yourArticle.getArticle();
		this.articleId = article.getArticleId();
		this.category = article.getCategory();
		this.date = article.getDate();
		this.url = article.getUrl();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.simplified = article.getSimplifiedContent();
		this.summary = article.getSummaryContent();
		this.viewedAt = yourArticle.getViewedAt();
	}
}
