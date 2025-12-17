package com.newsmoa.app.service;

import com.newsmoa.app.domain.Article;
import com.newsmoa.app.domain.MentionStats;
import com.newsmoa.app.dto.AssemblyMember;
import com.newsmoa.app.repository.ArticleRepository;
import com.newsmoa.app.repository.StatsRepository;
import com.newsmoa.app.util.AiUtil;
import com.newsmoa.app.util.CrawlingUtil;
import com.newsmoa.app.util.CsvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminService {

    private final List<String> categories = List.of( "경제", "과학", "사회", "세계", "문화"/**/ );
    
    private final ArticleRepository articleRepository;
    private final AiUtil aiUtil;
    private final StatsRepository statsRepository;

    @Autowired
    public AdminService(ArticleRepository articleRepository, AiUtil aiUtil, StatsRepository statsRepository) {
        this.articleRepository = articleRepository;
        this.aiUtil = aiUtil;
        this.statsRepository = statsRepository;
    }

    public void refreshArticle() {
        List<Article> updated = categories.stream()
                .map(CrawlingUtil::crawlArticles)
                .flatMap(List::stream)
//                .map(aiUtil::simplifyArticle)
//                .map(aiUtil::summarizeArticle)
//                .map(articleRepository::save) //임시저장
                .toList();
        articleRepository.saveAllArticles(updated);
        log.warn(updated.size() + " articles have been added");
        refreshStats(updated);
        log.warn("stats of " + updated.size() + " articles have been updated");
    }
    
    public void refreshStats(List<Article> newArticles) {
        Map<String, Long> countMap = new HashMap<>();
        List<AssemblyMember> memberList = CsvUtil.getMember();
        memberList.addAll(
                memberList.stream()
                        .map(AssemblyMember::partyName)
                        .distinct()
                        .map(partyName -> new AssemblyMember(partyName,partyName))
                        .toList()
        );
        statsRepository.deleteAll();
        Pattern pattern = Pattern.compile(
                memberList.stream()
                        .map(AssemblyMember::personName)
                        .distinct()
                        .sorted(Comparator.comparingInt(String::length).reversed())
                        .map(Pattern::quote)
                        .collect(Collectors.joining("|"))
        );

        newArticles.forEach(article -> {
            String text = article.getTitle() + " " + article.getContent();

            Set<String> mentioned = new HashSet<>();
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                mentioned.add(matcher.group());
            }

            mentioned.forEach(name ->
                    countMap.merge(name, 1L, Long::sum)
            );
        });

        List<MentionStats> result = memberList.stream()
                        .map(member -> 
                                new MentionStats(
                                        member.personName(),
                                        member.partyName(),
                                        countMap.get(member.personName())
                                ))
                        .toList();
        statsRepository.saveAll(result);
        log.debug("언급수 통계 계산됨: " + countMap.toString());
    }
}
