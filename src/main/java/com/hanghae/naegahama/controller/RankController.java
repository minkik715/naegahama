package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.rank.RankResponseDto;
import com.hanghae.naegahama.service.RankService;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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
