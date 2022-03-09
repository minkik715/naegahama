package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.repository.AnswerFileRepository;
import com.hanghae.naegahama.service.ShortsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortController {

    private final ShortsService shortService;

    /*@GetMapping("/api/shorts")
    public ResponseEntity<?> getThreeShorts(){
        return shortService.getThreeShorts();
    }*/

    @GetMapping("/api/shorts")
    public ResponseEntity<?> getOneShorts(){
        return shortService.getOneShorts();
    }

}
