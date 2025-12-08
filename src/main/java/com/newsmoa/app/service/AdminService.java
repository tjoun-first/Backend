package com.newsmoa.app.service;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.repository.ArticleRepository;
import com.newsmoa.app.util.AiUtil;
import com.newsmoa.app.util.CrawlingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdminService {

    private final List<String> categories = List.of( /*"경제", "과학", "사회",*/ "세계", "문화"/**/ );
    
    private final ArticleRepository articleRepository;
    private final AiUtil aiUtil;

    @Autowired
    public AdminService(ArticleRepository articleRepository, AiUtil aiUtil) {
        this.articleRepository = articleRepository;
        this.aiUtil = aiUtil;
    }

    public void refreshArticle() {
        List<Article> updated = categories.stream()
                .map(CrawlingUtil::crawlArticles)
                .flatMap(List::stream)
//                .map(aiUtil::simplifyArticle)
//                .map(aiUtil::summarizeArticle)
//                .map(articleRepository::save) //임시저장
                .toList();
        articleRepository.saveAll(updated);
        log.warn(updated.size() + " articles have been added");
    }
    
}
