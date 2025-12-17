package com.newsmoa.app.service;

import com.newsmoa.app.domain.MentionStats;
import com.newsmoa.app.repository.StatsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {
    private final StatsRepository statsRepository;

    public RankService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public List<String> getRankTop5(){
        List<MentionStats> stats = statsRepository.findTop5();
//        for(MentionStats stat:stats){
//            System.out.println("name: "+stat.getPersonName());
//            System.out.println("party: "+stat.getPartyName());
//            System.out.println("count: "+stat.getMentionCount());
//            System.out.println();
//        }
        return stats.stream().map(MentionStats::getPersonName).toList();
    }
}
