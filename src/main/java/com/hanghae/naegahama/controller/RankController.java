package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping("/api/rank")
    public ResponseEntity<?> getUserRank(){
        return rankService.getTop5Rank();
    }
}
