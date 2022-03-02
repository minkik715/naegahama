package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.rank.RankResponseDto;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final UserRepository userRepository;
    public ResponseEntity<?> getTop5Rank() {
        List<User> top5ByOrderByPointDesc = userRepository.findTop5ByOrderByPointDesc();
        List<RankResponseDto> rankResponseDtoList = new ArrayList<>();
        for(int i =1; i<=5; i++){
            RankResponseDto rankResponseDto = new RankResponseDto(top5ByOrderByPointDesc.get(i-1), i);
            rankResponseDtoList.add(rankResponseDto);
        }
        return ResponseEntity.ok().body(rankResponseDtoList);
    }
}
