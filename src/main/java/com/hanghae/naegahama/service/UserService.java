package com.hanghae.naegahama.service;

import com.hanghae.naegahama.comfortmethod.ComfortMethods;
import com.hanghae.naegahama.domain.Achievement;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.MyPage.*;
import com.hanghae.naegahama.dto.MyPage.MyAnswerDto;
import com.hanghae.naegahama.dto.MyPage.MyPostDto;
import com.hanghae.naegahama.dto.login.UserResponseDto;
import com.hanghae.naegahama.dto.user.UserInfoRequestDto;
import com.hanghae.naegahama.ex.UserNotFoundException;
import com.hanghae.naegahama.initial.Category;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final PostLikeRepository postLikeRepository;
    private final AnswerLikeRepository answerLikeRepository;

    public BasicResponseDto nicknameCheck(String nickname) {
        Optional<User> findNickname = userRepository.findByNickName(nickname);
        if (nickname.startsWith("HM") || findNickname.isPresent()) {
            return new BasicResponseDto("false");
        }
        return new BasicResponseDto("true");
    }

    public List<MyPostDto> myPost(UserDetailsImpl userDetails) {
        List<MyPostDto> myPageDtoList = new ArrayList<>();
        User user = userDetails.getUser();
        return getMyPostDtos(myPageDtoList, user);
    }

    public List<MyAnswerDto> myAnswer(UserDetailsImpl userDetails) {
        List<MyAnswerDto> myAnswerDtoList = new ArrayList<>();
        User user = userDetails.getUser();
        return getMyAnswerDtos(myAnswerDtoList, user);
    }


    @Transactional
    public MyAchievementDto myAchievement(UserDetailsImpl userDetails) {
        MyAchievementDto myAchievementDto = new MyAchievementDto();
        Achievement achievement = userDetails.getUser().getAchievement();
        ArrayList<Integer> achievementList = achievement.getAchievementList();
        for(int i =0; i<achievementList.size(); i++){
            myAchievementDto.getAchievement()[i] = achievementList.get(i);
        }
        return myAchievementDto;
    }

    public MyBannerDto myBanner(UserDetailsImpl userDetails) {
        return getMyBannerDto(userDetails.getUser());
    }


    public UserResponseDto userprofile(User user) {
        return new UserResponseDto(user);
    }

    @Transactional
    public BasicResponseDto setUserInfo(User user,UserInfoRequestDto userInfoRequestDto)
    {
        if(user.getUserStatus().equals("true")) {
            user.setBasicInfo(userInfoRequestDto);
            userRepository.save(user);
        }
        return new BasicResponseDto("true");
    }

    public MyCountDto mycount(UserDetailsImpl userDetails)
    {
        User user = userDetails.getUser();
        return new MyCountDto(
                user,postRepository.
                countByUser(user),answerRepository.
                countByUser(user));
    }

    public List<MyPostDto> userPost(Long userid)
    {
        List<MyPostDto> myPageDtoList = new ArrayList<>();
        User user = ComfortMethods.getUser(userid);
        return getMyPostDtos(myPageDtoList, user);
    }


    private List<MyPostDto> getMyPostDtos(List<MyPostDto> myPageDtoList, User user) {
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

    public List<MyAnswerDto> userAnswer(Long userid)
    {
        List<MyAnswerDto> myAnswerDtoList = new ArrayList<>();
        User user = ComfortMethods.getUser(userid);
        return getMyAnswerDtos(myAnswerDtoList, user);
    }

    private List<MyAnswerDto> getMyAnswerDtos(List<MyAnswerDto> myAnswerDtoList, User user) {
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

    public MyBannerDto userBanner(Long userid)
    {
        return getMyBannerDto(ComfortMethods.getUser(userid));
    }

    private MyBannerDto getMyBannerDto(User user) {
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

    public MyAchievementDto userAchievement(Long userid)
    {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFoundException("해당 글은 존재하지 않습니다."));

        MyAchievementDto myAchievementDto = new MyAchievementDto();
        Achievement achievement = user.getAchievement();
        ArrayList<Integer> achievementList = achievement.getAchievementList();
        for(int i =0; i<achievementList.size(); i++){
            myAchievementDto.getAchievement()[i] = achievementList.get(i);
        }
        return myAchievementDto;
    }


    public MyCountDto usercount(Long userid)
    {
        User user = ComfortMethods.getUser(userid);
        return new MyCountDto(user,
                postRepository.countByUser(user),
                answerRepository.countByUser(user));
    }
}