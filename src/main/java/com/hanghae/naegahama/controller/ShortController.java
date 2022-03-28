package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.shorts.ShortsResponseDto;
import com.hanghae.naegahama.service.ShortsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShortController {

    private final ShortsService shortService;

    /*@GetMapping("/api/shorts")
    public <?> getThreeShorts(){
        return shortService.getThreeShorts();
    }*/

    @GetMapping("/api/shorts")
    public List<ShortsResponseDto> getOneShorts(){
        return shortService.getOneShorts();
    }

}
