package com.newsmoa.app.repository;

import com.newsmoa.app.domain.YourArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MypageRepository extends JpaRepository<YourArticle, Long> {
    @Query("select ya " +
            "from YourArticle ya " +
            "join fetch ya.article " +
            "where ya.user.id=:userId " +
            "and ya.type='scraped'")
    List<YourArticle> findScrapedByUserId(@Param("userId")String userId);

    @Query("select ya " +
            "from YourArticle ya " +
            "join fetch ya.article " +
            "where ya.user.id=:userId " +
            "and ya.type='recent' " +
            "order by ya.viewedAt desc")
    List<YourArticle> findRecentByUserIdOrderByViewedAtDesc(@Param("userId") String userId);
    
    Optional<YourArticle> findByUserIdAndArticleArticleIdAndType(String userId, Long articleId, String type);
    
    Boolean existsByUserIdAndArticleArticleIdAndType(String userId, Long articleId, String type);



}
