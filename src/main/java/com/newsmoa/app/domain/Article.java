package com.newsmoa.app.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "article", uniqueConstraints = {
        @UniqueConstraint(name="uk_url",columnNames = {"url"})
})
public class Article {

	// PK: article_id. Long 타입이 일반적으로 ID로 많이 사용됨
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동 생성하도록 설정
	@Column(name = "article_id", nullable = false)
	private Long articleId; // NUMBER

	@Column(name = "category", length = 20, nullable = false)
	private String category; // varchar(20)

	// DATE 타입은 Java 8의 LocalDate로 매핑하는 것이 일반적입니다.
	@Column(name = "date", nullable = false)
	private LocalDate date; // DATE

	@Column(name = "url", length = 100, nullable = false, unique = true)
	private String url; // varchar(50)

	@Column(name = "title", length = 100, nullable = false)
	private String title; // varchar(100)
    
    @Column(name="img", length = 100)
    private String img;

	@Lob
	@Column(name = "content", length = 1000)
	private String content; // varchar(1000)

	@Lob
	@Column(name = "simplified_content", length = 1000)
	private String simplifiedContent; // varchar(1000)

	@Lob
	@Column(name = "summary_content", length = 1000)
	private String summaryContent; // varchar(1000)
    
    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;
    
    @PrePersist
    public void prePersist() {
        if (viewCount == null) viewCount = 0L;
    }
}