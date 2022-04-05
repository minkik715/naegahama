package com.hanghae.naegahama.service;

import com.hanghae.naegahama.repository.answerrepository.AnswerQuerydslRepository;
import com.hanghae.naegahama.repository.postrepository.PostQuerydslRepository;
import com.hanghae.naegahama.repository.userrepository.UserQuerydslRepository;
import com.hanghae.naegahama.repository.userrepository.UserRepository;
import com.hanghae.naegahama.util.ComfortMethods;
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
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.repository.achievementrepository.AchieveQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PostQuerydslRepository postQuerydslRepository;
    private final UserQuerydslRepository userQuerydslRepository;
    private final AnswerQuerydslRepository answerQuerydslRepository;
    private final AchieveQuerydslRepository achieveQuerydslRepository;

    public BasicResponseDto nicknameCheck(String nickname) {
        Optional<User> findNickname = userQuerydslRepository.findUserByNickanme(nickname);
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
        Achievement achievement = achieveQuerydslRepository.findAchievementByUser(userDetails.getUser());
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
                user,
                postQuerydslRepository.countPostByUser(user),
                answerQuerydslRepository.countAnswerByUser(user));
    }

    public List<MyPostDto> userPost(Long userid)
    {
        List<MyPostDto> myPageDtoList = new ArrayList<>();
        User user = ComfortMethods.getUser(userid);
        return getMyPostDtos(myPageDtoList, user);
    }


    private List<MyPostDto> getMyPostDtos(List<MyPostDto> myPageDtoList, User user) {
        List<Post> postList = postQuerydslRepository.findPostByUser(user.getId());
        for (Post post : postList)
        {
            MyPostDto postMyPageDto = new MyPostDto(post, user, (long) post.getPostLikes().size());

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
        List<Answer> answerList = answerQuerydslRepository.findAnswersByUser(user.getId());
        for (Answer answer : answerList)
        {

            MyAnswerDto myAnswerDto = new MyAnswerDto(answer, user,answer.getLikeList().size());
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
            //요기는 N+1문제가 아니다 단지 카테고리수 만큼 확인해야할뿐임
            if(answerQuerydslRepository.countByUserPostCate(user.getId(), cate) >=5){
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
                postQuerydslRepository.countPostByUser(user),
                answerQuerydslRepository.countAnswerByUser(user));
    }
}