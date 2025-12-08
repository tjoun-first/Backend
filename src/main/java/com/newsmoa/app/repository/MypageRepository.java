package com.newsmoa.app.repository;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.domain.User;
import com.newsmoa.app.domain.YourArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MypageRepository extends JpaRepository<YourArticle, Long> {
    List<YourArticle> findByUserAndType(User user, String type);
    Optional<YourArticle> findByUserAndArticleAndType(User user, Article article, String type);
    Boolean existsByUserIdAndArticleArticleIdAndType(String userId, Long articleId, String type);
    List<YourArticle> findByUserAndTypeOrderByViewedAtDesc(User user, String type);
}
