package com.newsmoa.app.dto;

import java.time.LocalDate;

import com.newsmoa.app.domain.Article;

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

	// Article 객체를 받아서 DTO로 변환하는 생성자 추가
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
}
