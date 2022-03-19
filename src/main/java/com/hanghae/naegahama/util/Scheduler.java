package com.hanghae.naegahama.util;

import com.hanghae.naegahama.alarm.AlarmType;
import com.hanghae.naegahama.domain.AnswerFile;
import com.hanghae.naegahama.domain.AnswerVideo;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.dto.event.AlarmEventListener;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.repository.AnswerFileRepository;
import com.hanghae.naegahama.repository.AnswerVideoRepository;
import com.hanghae.naegahama.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class Scheduler {

    private final PostRepository postRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    //로직구현
    //초 분 시간, 일 월 요일
    @Transactional
    @Scheduled(cron = "*/30 * * * * *")
    public void changeTime() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if (post.getDeadLine().getYear() != 2100) {
                if (post.getStatus().equals("opened") && post.getDeadLine().isBefore(LocalDateTime.now())) {
                    post.setStatus("closed");
                    applicationEventPublisher.publishEvent(new AlarmEventListener(post.getUser(),null,post, AlarmType.rate));
                }
            }
        }
    }


}
