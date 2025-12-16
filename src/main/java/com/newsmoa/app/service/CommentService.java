package com.newsmoa.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.domain.Comment;
import com.newsmoa.app.domain.User;
import com.newsmoa.app.dto.CommentResponse;
import com.newsmoa.app.repository.ArticleRepository;
import com.newsmoa.app.repository.CommentRepository;
import com.newsmoa.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    /**
     * 특정 기사에 대한 모든 댓글 목록을 정렬된 리스트 형태로 조회합니다.
     * (최상위 댓글 -> 답글 순서, 각각은 시간순으로 정렬)
     * @param articleId 기사 ID
     * @param currentUserId 현재 로그인한 사용자 ID
     * @return 정렬된 댓글 응답 DTO 목록 (isMine 정보 포함)
     */
    public List<CommentResponse> getCommentsByArticleId(Long articleId, String currentUserId) {
        return commentRepository.findCommentsByArticleIdSorted(articleId).stream()
                .map(comment -> new CommentResponse(comment, currentUserId))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse createComment(Long articleId, Long parentCommentId, String content, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + articleId));

        Comment parentComment = null;
        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found with id: " + parentCommentId));

            // 답글의 답글은 생성하지 못하도록 막는 로직
            if (parentComment.getParentComment() != null) {
                throw new IllegalArgumentException("Cannot create a reply to a reply.");
            }
        }

        Comment newComment = Comment.builder()
                .content(content)
                .user(user)
                .article(article)
                .parentComment(parentComment)
                .likeCount(0L)
                .build();

        Comment savedComment = commentRepository.save(newComment);

        return new CommentResponse(savedComment, userId);
    }

    @Transactional
    public void deleteComment(Long commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, String userId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not authorized to update this comment(현재 사용자와 수정하려는 댓글을 작성한 사용자가 다르다궁...)");
        }

        comment.setContent(newContent);
        // Spring Data JPA의 @Transactional 덕분에 별도의 save 호출 없이 변경 감지 (dirty checking)를 통해 업데이트됩니다.
        return new CommentResponse(comment, userId);
    }
}
