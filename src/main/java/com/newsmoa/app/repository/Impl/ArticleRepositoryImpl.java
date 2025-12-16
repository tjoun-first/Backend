package com.newsmoa.app.repository.Impl;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.repository.ArticleRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;

    @Autowired    
    public ArticleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAllArticles(List<Article> articles) {
        String sql = """
            insert ignore
            into article(category,date,url,title,img,content,view_count)
            values (?,?,?,?,?,?,?) 
        """;
        jdbcTemplate.batchUpdate(
                sql,
                articles,
                articles.size(),
                (PreparedStatement ps, Article article) -> {
                    ps.setString(1, article.getCategory());
                    ps.setDate(2, Date.valueOf(article.getDate()));
                    ps.setString(3, article.getUrl());
                    ps.setString(4, article.getTitle());
                    ps.setString(5, article.getImg());
                    ps.setString(6, article.getContent());
                    ps.setLong(7, article.getViewCount());
                }
        );
    }
}
