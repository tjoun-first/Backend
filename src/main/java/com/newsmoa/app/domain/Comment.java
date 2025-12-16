package com.newsmoa.app.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "content" , length = 255,  nullable = false)
    private String content;  // 댓글 내용
    
    // User와의 관계 설정: Many (YourArticle) to One (User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // user_id 컬럼을 외래 키로 사용
    private User user; // 작성자 id
    
    @Column(name = "like_count", nullable = false)
    private Long likeCount = 0L;  // 좋아요 수
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // 작성일
    
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;  // 댓글이 달린 기사

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id" ) // 셀프 조인
    private Comment parentComment;  // 부모 댓글 (대댓글을 위한)
    
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>(); // 대댓글 기능을 위한 양방향 셀프 참조(부모 댓글 제거시 자식 댓글들 전부 같이 삭제)
}

