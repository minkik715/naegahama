package com.hanghae.naegahama.service;

import com.hanghae.naegahama.security.UserDetailsImpl;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.survey.CommendResponseDto;
import com.hanghae.naegahama.dto.survey.SurveyRequestDto;
import com.hanghae.naegahama.dto.survey.SurveyresponseDto;
import com.hanghae.naegahama.repository.PostRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class SurveyService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //설문을 바탕으로 유저에게 하마 만들어주기.
    @Transactional
    public void createHippo(SurveyRequestDto surveyRequestDto, User user)
    {

        ArrayList<Long> longs = new ArrayList<>();
        for (Long aLong : surveyRequestDto.getResult()) {
            longs.add(aLong);
        }

        int emotion = 0; // answer값 0으로 초기화
        int plan = 0; // answer값 0으로 초기화
        int action = 0; // answer값 0으로 초기화

        for (int i = 0; i < surveyRequestDto.getResult().length; i++) // 전달받은 배열의 길이만큼 반복
        {
            if (longs.get(i) == 1 && (i == 0 || i == 4 || i == 6)) { // 배열 i번 인덱스가 1일때
                emotion++; // emotion에 1를 더함
            }
            else if (longs.get(i) == 1 && (i == 1 || i == 2 || i == 7)) { // 배열 i번 인덱스가 1일때
                plan++; // plan에 1를 더함
            }
            else if (longs.get(i) == 1 && (i == 3 || i == 5 || i == 8)) { // 배열 i번 인덱스가 1일때
                action++; // action에 1를 더함
            }
        }

        //결과 도출.
        String emotion2 = "";
        String plan2 = "";
        String action2 = "";

        if (emotion >= 2) { //emotion이 2보다 크거나 같을때
            emotion2 = "감성";
        } else if (emotion < 2) {
            emotion2 = "이성";
        }
        if (plan >= 2) { //emotion이 2보다 크거나 같을때
            plan2 = "계획";
        } else if (plan < 2) {
            plan2 = "직관";
        }
        if (action >= 2) { //emotion이 2보다 크거나 같을때
            action2 = "외향";
        } else if (action < 2) {
            action2 = "내향";
        }

        //하마 이름 생성.
        String hippo = "";

        if (emotion2.equals("감성") && plan2.equals("계획") && action2.equals("외향")  ) {
            hippo = "열심히 노력 하마";
        } else if (emotion2.equals("감성") && plan2.equals("계획") && action2.equals("내향")) {
            hippo = "외유내강 하마";
        } else if (emotion2.equals("감성") && plan2.equals("직관") && action2.equals("외향")) {
            hippo = "하마 냄새가 나는 하마";
        } else if (emotion2.equals("감성") && plan2.equals("직관") && action2.equals("내향")) {
            hippo = "스윗 하마";
        } else if (emotion2.equals("이성") && plan2.equals("계획")&& action2.equals("외향")) {
            hippo = "내가 리더 하마";
        } else if (emotion2.equals("이성") && plan2.equals("계획") && action2.equals("내향")) {
            hippo = "스마트 하마";
        } else if (emotion2.equals("이성") && plan2.equals("직관") && action2.equals("외향")) {
            hippo = "세상 시원시원한 하마";
        } else if (emotion2.equals("이성") && plan2.equals("직관") && action2.equals("내향")) {
            hippo = "센치 하마";
        }
        user.setHippoName(hippo);


        // 최초 평가시 업적 6 획득
        User achievementUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        achievementUser.getAchievement().setAchievement5(1);


        userRepository.save(user);

    }


    //설문조사 결과
    public SurveyresponseDto getHippo(UserDetailsImpl userDetails) {
        String hippoName = userDetails.getUser().getHippoName();

        SurveyresponseDto surveyresponseDto = new SurveyresponseDto(
                hippoName
        );
        return surveyresponseDto;
    }

    //같은 하마의 요청글 추천.
    public List<CommendResponseDto> recommend(String hippoName) {

        //하마 이름이 같은 게시글을 포스트 레포지토리에서 가져온다.
//        User user = userDetails.getUser();
        List<Post> posts = postRepository.findAllByUserHippoName(hippoName);
        List<CommendResponseDto> commendResponseDtos = new ArrayList<>();

        //랜덤 숫자 두개를 추출한다.
        int size = posts.size();
        int min = 0;
        int random = (int) ((Math.random() * (size - min)) + min);

        int random2 = -1;
        while (true) {
            random2 = (int) ((Math.random() * (size - min)) + min);
            if (random != random2) {
                break;
            }
        }

        //요청게시글에서 램덤으로 두개를 가져온다.
        Post post1 = posts.get(random);
        Post post2 = posts.get(random2);

        CommendResponseDto commendResponseDto = new CommendResponseDto(
                post1.getId(),
                post1.getTitle(),
                post2.getId(),
                post2.getTitle()
        );

        commendResponseDtos.add(commendResponseDto);

        return commendResponseDtos;
    }
}