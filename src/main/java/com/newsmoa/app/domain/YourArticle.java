package com.newsmoa.app.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "your_article")
public class YourArticle {

    // PK: no
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySql에서 no BIGINT NOT NULL AUTO_INCREMENT 로 적용
    private Long no; // Type

    @Column(name = "type", nullable = false)
    private String type; // Type (recent or scraped)
    
    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    // User와의 관계 설정: Many (YourArticle) to One (User)
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정 (성능 최적화)
    @JoinColumn(name = "user_id", nullable = false) // user_id 컬럼을 외래 키로 사용
    private User user;

    // Article과의 관계 설정: Many (YourArticle) to One (Article)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false) // article_id 컬럼을 외래 키로 사용
    private Article article;
    
}
