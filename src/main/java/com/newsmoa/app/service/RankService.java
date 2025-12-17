package com.newsmoa.app.service;

import com.newsmoa.app.dto.MentionStatResponse;
import com.newsmoa.app.repository.StatsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {
    private final StatsRepository statsRepository;

    public RankService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Transactional
    public List<MentionStatResponse> getRankTop5(){
        List<MentionStatResponse> stats = 
                statsRepository.findTop5().stream()
                        .map(ms -> new MentionStatResponse(ms.getPersonName(),ms.getMentionCount()))
                        .toList();
        return stats;
    }
    
    @Transactional
    public List<MentionStatResponse> getPartyStats(){
        List<MentionStatResponse> partyStats = 
                statsRepository.findPartyStats().stream()
                        .map(stat -> new MentionStatResponse(stat.getPartyName(), stat.getCnt()))
                        .toList();
        return partyStats;
    }
}
