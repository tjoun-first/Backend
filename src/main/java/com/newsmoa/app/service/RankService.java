package com.newsmoa.app.service;

import com.newsmoa.app.domain.MentionStats;
import com.newsmoa.app.dto.MentionStatResponse;
import com.newsmoa.app.repository.StatsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {
    private final StatsRepository statsRepository;

    public RankService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public List<MentionStatResponse> getRankTop5(){
        List<MentionStatResponse> stats = 
                statsRepository.findTop5().stream()
                        .map(ms -> new MentionStatResponse(ms.getPersonName(),ms.getMentionCount()))
                        .toList();
                
        return stats;
    }
    
    
}
