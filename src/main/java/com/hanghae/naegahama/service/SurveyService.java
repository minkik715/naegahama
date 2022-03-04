package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;

import com.hanghae.naegahama.dto.post.PostResponseDto;
import com.hanghae.naegahama.dto.post.ResponseDto;
import com.hanghae.naegahama.dto.survey.SurveyRequestDto;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@EnableAutoConfiguration
@RequiredArgsConstructor
@Service
@Slf4j
public class SurveyService {

    private final UserRepository userRepository;

    //설문을 바탕으로 유저에게 하마 만들어주기.
    @Transactional
    public ResponseEntity createHippo(SurveyRequestDto surveyRequestDto, User user) {


        ArrayList<Long> longs = new ArrayList<>();
        for (Long aLong : surveyRequestDto.getNum()) {
            longs.add(aLong);
        }

        int emotion = 0; // answer값 0으로 초기화
        int plan = 0; // answer값 0으로 초기화
        int action = 0; // answer값 0으로 초기화

        for (int i = 0; i < surveyRequestDto.getNum().length; i++) // 전달받은 배열의 길이만큼 반복
        {
            if (longs.get(i) == 1 && (i == 1 || i == 5 || i == 7)) { // 배열 i번 인덱스가 1일때
                emotion += emotion; // emotion에 1를 더함
            }
            if (longs.get(i) == 1 && (i == 2 || i == 3 || i == 8)) { // 배열 i번 인덱스가 1일때
                plan += plan; // plan에 1를 더함
            }
            if (longs.get(i) == 1 && (i == 4 || i == 6 || i == 9)) { // 배열 i번 인덱스가 1일때
                action += action; // action에 1를 더함
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
        } else if (plan >= 2) { //emotion이 2보다 크거나 같을때
            plan2 = "계획";
        } else if (plan < 2) {
            plan2 = "직관";
        } else if (action >= 2) { //emotion이 2보다 크거나 같을때
            action2 = "외향";
        } else if (action < 2) {
            action2 = "내향";
        }

        //하마 이름 생성.
        String hippo = "";

        if (action2 == "감성" && plan2 == "계획" && action2 == "외향") {
            hippo = "열심히 노력 하마";
        } else if (action2 == "감성" && plan2 == "계획" && action2 == "내향") {
            hippo = "외유내강 하마";
        } else if (action2 == "감성" && plan2 == "직관" && action2 == "외향") {
            hippo = "하마 냄새가 나는 하마";
        } else if (action2 == "감성" && plan2 == "직관" && action2 == "내향") {
            hippo = "스윗 하마";
        } else if (action2 == "이성" && plan2 == "계획" && action2 == "외향") {
            hippo = "내가 리더 하마";
        } else if (action2 == "이성" && plan2 == "계획" && action2 == "내향") {
            hippo = "스마트 하마";
        } else if (action2 == "이성" && plan2 == "직관" && action2 == "외향") {
            hippo = "세상 시원시원한 하마";
        } else if (action2 == "이성" && plan2 == "직관" && action2 == "내향") {
            hippo = "센치 하마";
        }
        user.setHippoName(hippo);
        return ResponseEntity.ok().body(userRepository.save(user));
    }
}


//    //설문조사 결과
//    @ResponseBody
//    public PostResponseDto getHippo() {
//
//        User user = userRepository.findBy();
//        List<PostResponseDto> response = new ArrayList<>();
//
//        for (Post post : posts) {
//            Integer answerCount = answerRepository.countByPost(post);
//            Long postLikeCount = postLikeRepository.countByPost(post);
//            PostResponseDto postResponseDto = new PostResponseDto(
//                    post.getId(),
//                    post.getTitle(),
//                    post.getContent(),
//                    post.getModifiedAt(),
//                    answerCount,
//                    postLikeCount
//            );
//            response.add(postResponseDto);
//        }
//        return response;
//    }
//
//    //요청글 상세조회.
//    @ResponseBody
//    public List<ResponseDto> getPost1(Long postId) {
//        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(postId);
//        List<ResponseDto> response = new ArrayList<>();
//
//        for (Post post : posts) {
//            Integer answerCount = answerRepository.countByPost(post);
//            Long postLikeCount = postLikeRepository.countByPost(post);
//            ResponseDto ResponseDto = new ResponseDto(
//                    post.getId(),
//                    post.getTitle(),
//                    post.getContent(),
//                    post.getModifiedAt(),
//                    answerCount,
//                    post.getUser().getId(),
//                    post.getUser().getNickName(),
//                    postLikeCount
//            );
//            response.add(ResponseDto);
//        }
//        return response;
//    }
//
//}
