/*
package com.hanghae.naegahama.util;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Scheduler {

    private final PostRepository postRepository;

    //로직구현
    //초 분 시간, 일 월 요일
    public void changeTime(){
        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            if(post>30){
            post.setTimeSet(post.getTimeSet()-5);

            }
    }
}
*/
