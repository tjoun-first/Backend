package com.newsmoa.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsmoa.app.dto.CommentResponse;
import com.newsmoa.app.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/article/{article_id}/comment")
    public ResponseEntity<CommentResponse> createComment(
    		@PathVariable("article_id") Long article_id,
            @RequestParam(value="parent_comment_id",  required=false) Long parentCommentId,
            @RequestBody String content,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        CommentResponse createdComment = commentService.createComment(article_id, parentCommentId, content, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping("/article/{article_id}/comment")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("article_id") Long articleId,
            @RequestParam("comment_id") Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
