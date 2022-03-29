package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.rank.RankResponseDto;
import com.hanghae.naegahama.service.RankService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping("/api/rank")
    public List<RankResponseDto> getUserRank(){
        return rankService.getTop5Rank();
    }
}
