package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Achievement;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.MyPage.*;
import com.hanghae.naegahama.dto.MyPage.MyAnswerDto;
import com.hanghae.naegahama.dto.MyPage.MyPostDto;
import com.hanghae.naegahama.dto.login.UserResponseDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.dto.user.UserInfoRequestDto;
import com.hanghae.naegahama.handler.ex.PasswordCheckFailException;
import com.hanghae.naegahama.initial.Category;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //
    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final PostLikeRepository postLikeRepository;
    private final AnswerLikeRepository answerLikeRepository;



    public ResponseEntity<?> nicknameCheck(String nickname) {
        Optional<User> findNickname = userRepository.findByNickName(nickname);
        if (findNickname.isPresent()) {
            return ResponseEntity.ok().body(new BasicResponseDto("false"));
        }
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public List<MyPostDto> myPost(UserDetailsImpl userDetails) {
        List<MyPostDto> myPageDtoList = new ArrayList<>();

        User user = userDetails.getUser();

        List<Post> postList = postRepository.findAllByUserOrderByModifiedAtDesc(user);

        for (Post post : postList)
        {
            MyPostDto postMyPageDto = new MyPostDto(post, user,postLikeRepository.countByPost(post));

            if ( post.getFileList().size() != 0)
            {
                postMyPageDto.setImg(post.getFileList().get(0).getUrl());
            }
            myPageDtoList.add(postMyPageDto);
        }

        return myPageDtoList;
    }

    public List<MyAnswerDto> myAnswer(UserDetailsImpl userDetails) {
        List<MyAnswerDto> myAnswerDtoList = new ArrayList<>();
        User user = userDetails.getUser();

        List<Answer> answerList = answerRepository.findAllByUserOrderByModifiedAtDesc(user);
        for (Answer answer : answerList)
        {

            MyAnswerDto myAnswerDto = new MyAnswerDto(answer, user,answerLikeRepository.countByAnswer(answer));

            if ( answer.getFileList().size() != 0)
            {
                myAnswerDto.setImg(answer.getFileList().get(0).getUrl());
            }
            myAnswerDtoList.add(myAnswerDto);
        }
        return myAnswerDtoList;
    }


    public MyAchievementDto myAchievement(UserDetailsImpl userDetails) {
        MyAchievementDto myAchievementDto = new MyAchievementDto();
        Achievement achievement = userDetails.getUser().getAchievement();

        myAchievementDto.getAchievement()[0] = achievement.getAchievement1();
        myAchievementDto.getAchievement()[1] = achievement.getAchievement2();
        myAchievementDto.getAchievement()[2] = achievement.getAchievement3();
        myAchievementDto.getAchievement()[3] = achievement.getAchievement4();
        myAchievementDto.getAchievement()[4] = achievement.getAchievement5();
        myAchievementDto.getAchievement()[5] = achievement.getAchievement6();
        myAchievementDto.getAchievement()[6] = achievement.getAchievement7();
        myAchievementDto.getAchievement()[7] = achievement.getAchievement8();


        // 업적 1 : answerService.answerStar      [ 최초 answer 글 1점 획득 ]
        // 업적 2 : answerService.answerStar      [ 최초 answer 글 5점 획득 ]
        // 업적 3 :                               [ 최초 검색기능 사용 - 미구현 ]
        // 업적 4 : commentService.writeComment   [ 최초 comment 작성 ]
        // 업적 5 : postService.createPost        [ 최초 post 글 작성 ]
        // 업적 6 : surveyService.createHippo     [ 최초 survay 설문조사 완료 ]
        // 업적 7 : answerService.answerStar      [ 최초 answer 글 평가 ]
        // 업적 8 : answerService.answerWrite;    [ 최초 answer 글 작성 ]

        return myAchievementDto;
    }

    public MyBannerDto myBanner(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        ArrayList<String> expert = new ArrayList<>();
        for (String cate : Category.category) {
            if(answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, cate, 4) >=5){
                expert.add(cate);
            }
        }

        int point = user.getPoint();
        if(point <3000){
             point = point % 1000;
        }

        return new MyBannerDto(user,expert,point);
    }


    public ResponseEntity<?> userprofile(User user) {
        UserResponseDto userResponse = new UserResponseDto(user);
        return ResponseEntity.ok().body(userResponse);
    }


    // 하나의 트랜젝션이 끝나면 1차 영속성 컨텍스트는 초기화된다.
    //1차 영속성 컨텍스트에 안들어 가있기 떄문에 save를 해줘야 하는거였네요!
    public ResponseEntity<?> setUserInfo(User user,UserInfoRequestDto userInfoRequestDto)
    {
        user.setBasicInfo(userInfoRequestDto);
        userRepository.save(user);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public MyCountDto mycount(UserDetailsImpl userDetails)
    {
        User user = userDetails.getUser();

        Long postCount = postRepository.countByUser(user);
        Long answerCount = answerRepository.countByUser(user);

        return new MyCountDto(user,postCount,answerCount);
    }
}
