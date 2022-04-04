package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.repository.postrepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final PostRepository postRepository;

    @GetMapping("/health") 
    public String checkHealth() { 
        return "healthy"; 
    }


    @GetMapping("/test")
    public void test(){
        postRepository.findAll();
    }
}
