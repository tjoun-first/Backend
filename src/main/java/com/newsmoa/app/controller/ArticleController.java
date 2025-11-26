package com.newsmoa.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class ArticleController {

    private final ArticleService articleService;

    
    @GetMapping("/{category}")
    // 수정: @PathVariable("category")로 이름 명시
    public List<Article> getArticlesByCategory(@PathVariable("category") String category) { 
        return articleService.findArticlesByCategory(category);
    }
}
