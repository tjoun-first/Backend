package com.newsmoa.app.service;

import com.newsmoa.app.domain.Comment;
import com.newsmoa.app.dto.CommentResponse;
import com.newsmoa.app.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final static String APPROVED_STATUS = "approved";

    /**
     * 특정 기사에 대한 승인된 댓글 목록을 계층적으로 조회합니다.
     * @param articleId 기사 ID
     * @return 계층 구조를 가진 댓글 응답 DTO 목록
     */
    public List<CommentResponse> getCommentsByArticleId(Long articleId) {
        // 1. 기사에 해당하는 승인된 최상위 댓글을 조회합니다.
        List<Comment> topLevelComments = commentRepository.findByArticle_ArticleIdAndParentCommentIsNullAndStatusOrderByCreatedAtAsc(articleId, APPROVED_STATUS);

        // 2. 최상위 댓글들을 CommentResponse DTO로 변환합니다.
        //    이 과정에서 자식 댓글들도 재귀적으로 필터링하고 변환합니다.
        return topLevelComments.stream()
                .map(this::mapCommentToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Comment 엔티티를 CommentResponse DTO로 변환합니다.
     * 자식 댓글들도 재귀적으로 'approved' 상태인 것만 필터링하여 포함시킵니다.
     * @param comment 변환할 Comment 엔티티
     * @return 변환된 CommentResponse DTO
     */
    private CommentResponse mapCommentToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setCommentId(comment.getCommentId());
        response.setContent(comment.getContent());
        if (comment.getUser() != null) {
            response.setUserId(comment.getUser().getId());
        }
        response.setStatus(comment.getStatus());
        response.setLikeCount(comment.getLikeCount());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());
        if (comment.getArticle() != null) {
            response.setArticleId(comment.getArticle().getArticleId());
        }
        if (comment.getParentComment() != null) {
            response.setParentCommentId(comment.getParentComment().getCommentId());
        }
        
        // 자식 댓글들 중 'approved' 상태인 것만 재귀적으로 필터링하고 DTO로 변환
        if (comment.getChildren() != null && !comment.getChildren().isEmpty()) {
            List<CommentResponse> approvedChildren = comment.getChildren().stream()
                    .filter(child -> APPROVED_STATUS.equals(child.getStatus()))
                    .map(this::mapCommentToResponse) // 재귀 호출
                    .collect(Collectors.toList());
            response.setChildren(approvedChildren);
        }
        
        return response;
    }
}
