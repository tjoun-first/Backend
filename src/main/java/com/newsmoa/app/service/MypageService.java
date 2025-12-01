package com.newsmoa.app.service;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.domain.User;
import com.newsmoa.app.domain.YourArticle;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.repository.MypageRepository;
import com.newsmoa.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;

    public List<ArticleResponse> getScrapedArticlesByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        List<YourArticle> scrapedEntries = mypageRepository.findByUserAndType(user, "scraped");

        List<ArticleResponse> scrapedArticles = scrapedEntries.stream()
                .map(YourArticle::getArticle)
                .map(ArticleResponse::new)
                .toList();
        
        return scrapedArticles;
    }

    public List<ArticleResponse> getRecentArticlesByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        List<YourArticle> recentEntries = mypageRepository.findByUserAndType(user, "recent");

        List<ArticleResponse> recentArticles = recentEntries.stream()
                .map(YourArticle::getArticle)
                .map(ArticleResponse::new)
                .toList();

        return recentArticles;
    }
}
