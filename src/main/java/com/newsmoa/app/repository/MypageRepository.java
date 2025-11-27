package com.newsmoa.app.repository;

import com.newsmoa.app.domain.User;
import com.newsmoa.app.domain.YourArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypageRepository extends JpaRepository<YourArticle, Long> {
    List<YourArticle> findByUserAndType(User user, String type);
}
