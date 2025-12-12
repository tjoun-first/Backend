package com.newsmoa.app.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.newsmoa.app.domain.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String userId; // 타입을 String으로 변경, username 필드 삭제
    private String status;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long articleId;
    private Long parentCommentId;
    private List<CommentResponse> children; // 답글 (대댓글) 리스트

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        if (comment.getUser() != null) {
            this.userId = comment.getUser().getId(); // id 할당, username 할당 로직 삭제
        }
        this.status = comment.getStatus();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        if (comment.getArticle() != null) {
            this.articleId = comment.getArticle().getArticleId();
        }
        if (comment.getParentComment() != null) {
            this.parentCommentId = comment.getParentComment().getCommentId();
        }
        // 자식 댓글이 있는 경우 CommentResponse DTO로 변환하여 할당
        if (comment.getChildren() != null && !comment.getChildren().isEmpty()) {
            this.children = comment.getChildren().stream()
                    .map(CommentResponse::new) // 재귀적으로 CommentResponse 생성
                    .collect(Collectors.toList());
        }
    }
}
