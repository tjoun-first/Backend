package com.newsmoa.app.service;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.domain.User;
import com.newsmoa.app.domain.YourArticle;
import com.newsmoa.app.repository.ArticleRepository;
import com.newsmoa.app.dto.ArticleResponse;
import com.newsmoa.app.repository.MypageRepository;
import com.newsmoa.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

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

    @Transactional
    public void saveRecentArticle(String userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + articleId));

        // Check if the "recent" article entry already exists for this user and article
        mypageRepository.findByUserAndArticleAndType(user, article, "recent").ifPresent(mypageRepository::delete);

        YourArticle yourArticle = new YourArticle();
        yourArticle.setUser(user);
        yourArticle.setArticle(article);
        yourArticle.setType("recent");

        mypageRepository.save(yourArticle);
    }

    @Transactional
    public void saveScrapedArticle(String userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + articleId));

        // Check if the "scraped" article entry already exists for this user and article
        mypageRepository.findByUserAndArticleAndType(user, article, "scraped").ifPresent(mypageRepository::delete);

        YourArticle yourArticle = new YourArticle();
        yourArticle.setUser(user);
        yourArticle.setArticle(article);
        yourArticle.setType("scraped");

        mypageRepository.save(yourArticle);
    }

    @Transactional
    public void deleteScrapedArticle(String userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + articleId));

        mypageRepository.findByUserAndArticleAndType(user, article, "scraped")
                .ifPresent(mypageRepository::delete);
    }

    @Transactional
    public Boolean checkArticleScrapedBy(Long articleId, String userId) {
        return mypageRepository.existsByUserIdAndArticleArticleIdAndType(userId, articleId, "scraped");
    }
}
