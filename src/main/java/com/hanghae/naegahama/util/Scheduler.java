package com.hanghae.naegahama.util;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.event.PostClosedEvent;
import com.hanghae.naegahama.repository.postrepository.PostQuerydslRepository;
import com.hanghae.naegahama.repository.postrepository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class Scheduler {

    private final PostQuerydslRepository postQuerydslRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final RedissonClient redissonClient;


    //로직구현
    //초 분 시간, 일 월 요일



    @Transactional
    @Scheduled(cron = "*/30 * * * * *")
    public void changeTime() {
        RLock lock = redissonClient.getLock("hide");
        try {
            boolean isLocked = lock.tryLock(4, 5, TimeUnit.SECONDS);
            if (!isLocked) {
                log.info("LOCK 획득 실패");
            }
        } catch (InterruptedException e) {
            log.error(e.toString());
        } finally {
            lock.unlock();
        }

        for (Post post : postQuerydslRepository.findPostByOpenedStatus()) {
            post.setStatus("closed");
            applicationEventPublisher.publishEvent(new PostClosedEvent(post.getUser(), post));
        }
    }
}



//락을 건다 -> 레디스  -> 레디스에 알람보내기 레디스
