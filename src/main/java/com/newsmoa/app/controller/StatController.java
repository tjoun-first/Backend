package com.newsmoa.app.controller;

import com.newsmoa.app.dto.MentionStatResponse;
import com.newsmoa.app.service.RankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stat")
public class StatController {
    private final RankService rankService;

    public StatController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("/ranked-person")
    public List<MentionStatResponse> getRankedPerson(){
        return rankService.getRankTop5();
    }
}
