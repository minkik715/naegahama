package com.hanghae.naegahama.service;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.survey.CommendResponseDto;
import com.hanghae.naegahama.dto.survey.SurveyRequestDto;
import com.hanghae.naegahama.dto.survey.SurveyresponseDto;
import com.hanghae.naegahama.event.SurveyEvent;
import com.hanghae.naegahama.initial.HippoResult;
import com.hanghae.naegahama.initial.SurveyResult;
import com.hanghae.naegahama.repository.postlikerepository.PostLikeQuerydslRepository;
import com.hanghae.naegahama.repository.postrepository.PostQuerydslRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class SurveyService {


    private final PostQuerydslRepository postQuerydslRepository;

    private final PostLikeQuerydslRepository postLikeQuerydslRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public BasicResponseDto createHippo(SurveyRequestDto surveyRequestDto, User user)
    {

        String hippo = SurveyResult.hippoMap.get(
                        surveyRequestDto.isEmotion() +
                        surveyRequestDto.isPlan() +
                        surveyRequestDto.isAction()
        );
        applicationEventPublisher.publishEvent(new SurveyEvent(user,hippo));
        log.info(hippo);
        return new BasicResponseDto("success");
    }


    //설문조사 결과
    public SurveyresponseDto getHippo(UserDetailsImpl userDetails) {
        String hippoName = userDetails.getUser().getHippoName();
        String imgUrl = HippoResult.resultImage(hippoName);
        String surveyResult = HippoResult.resultText(hippoName);
        String nickname = userDetails.getUser().getNickName();

        SurveyresponseDto surveyresponseDto = new SurveyresponseDto(
                nickname,
                hippoName,
                imgUrl,
                surveyResult
        );
        return surveyresponseDto;
    }

    @Transactional(readOnly = true)
    public List<CommendResponseDto> recommend(String hippoName) {
        System.out.println(2);
        List<Post> posts = postQuerydslRepository.findPostByHippoName(hippoName);
        List<CommendResponseDto> commendResponseDtos = new ArrayList<>();
        if(posts.size() <2){
            return commendResponseDtos;
        }
        int size = posts.size();
        int min = 0;
        int random = (int) ((Math.random() * (size - min)) + min);
        int random2 = -1;
        while (true) {
            random2 = (int) ((Math.random() * (size - min)) + min);
            log.info(String.valueOf(random2));
            if (random != random2) {
                break;
            }
        }
        Post post1 = posts.get(random);
        Post post2 = posts.get(random2);
        Long countByPost1 = postLikeQuerydslRepository.countPostLikes(post1.getId());
        Long countByPost2 = postLikeQuerydslRepository.countPostLikes(post2.getId());
        commendResponseDtos.add(new CommendResponseDto(post1,countByPost1));
        commendResponseDtos.add(new CommendResponseDto(post2,countByPost2));

        return commendResponseDtos;
    }
}