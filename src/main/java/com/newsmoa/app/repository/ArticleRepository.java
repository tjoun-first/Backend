package com.newsmoa.app.repository;

import com.newsmoa.app.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategory(String category);

    @Query(value = "SELECT * FROM article WHERE MATCH(title, content) AGAINST(:keyword IN BOOLEAN MODE)", nativeQuery = true)
    List<Article> searchByKeyword(@Param("keyword") String keyword);

    List<Article> findTop5ByOrderByViewCountDesc();
}
