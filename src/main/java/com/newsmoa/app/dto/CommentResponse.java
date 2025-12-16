package com.newsmoa.app.dto;

import java.time.LocalDateTime;

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
    private String userId;
    private Long likeCount;
    private LocalDateTime createdAt;
    private Long articleId;
    private Long parentCommentId;
    private Boolean isMine;

    public CommentResponse(Comment comment, String currentUserId) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        if (comment.getUser() != null) {
            this.userId = comment.getUser().getId();
            this.isMine = this.userId.equals(currentUserId);
        } else {
            this.isMine = false;
        }
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        if (comment.getArticle() != null) {
            this.articleId = comment.getArticle().getArticleId();
        }
        if (comment.getParentComment() != null) {
            this.parentCommentId = comment.getParentComment().getCommentId();
        }
    }
}
