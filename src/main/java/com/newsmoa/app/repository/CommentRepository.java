package com.newsmoa.app.repository;

import com.newsmoa.app.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 특정 기사에 달린 승인된 최상위 댓글 (대댓글이 아닌 댓글) 목록을 조회합니다.
     * @param articleId 기사 ID
     * @param status 댓글 상태 (e.g., "approved")
     * @return 승인된 최상위 댓글 목록
     */
    List<Comment> findByArticle_ArticleIdAndParentCommentIsNullAndStatusOrderByCreatedAtAsc(Long articleId, String status);
}
