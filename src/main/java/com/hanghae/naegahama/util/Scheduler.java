package com.hanghae.naegahama.util;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.event.PostClosedEvent;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.repository.postrepository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.List;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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
       /* List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if (post.getDeadLine().getYear() != 2100) {
                if (post.getStatus().equals("opened") && post.getDeadLine().isBefore(LocalDateTime.now())) {
                    post.setStatus("closed");
                    applicationEventPublisher.publishEvent(new PostClosedEvent(post.getUser(),post));
                }
            }
        }*/
    }

//락을 건다 -> 레디스  -> 레디스에 알람보내기 레디스
}
