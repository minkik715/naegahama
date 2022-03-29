package com.hanghae.naegahama.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.MyPage.MyAchievementDto;
import com.hanghae.naegahama.dto.MyPage.MyBannerDto;
import com.hanghae.naegahama.dto.MyPage.*;
import com.hanghae.naegahama.dto.login.LoginResponseDto;
import com.hanghae.naegahama.dto.login.UserResponseDto;
import com.hanghae.naegahama.dto.signup.NickNameDuplicateCheckDto;
import com.hanghae.naegahama.dto.user.UserInfoRequestDto;
import com.hanghae.naegahama.ex.ErrorResponse;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.KakaoUserService;
import com.hanghae.naegahama.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;


    @PostMapping("/user/nickname")
    public BasicResponseDto emailCheck(@RequestBody @Validated NickNameDuplicateCheckDto nickNameDuplicateCheckDto){
        return userService.nicknameCheck(nickNameDuplicateCheckDto.getNickname());
    }

    @PostMapping("/user/kakaoLogin")
    public LoginResponseDto login(@RequestBody @Validated Map<String, Object> param, HttpServletResponse response) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(param.get("kakaoToken").toString(), response);
    }

    @PostMapping("/userinfo")
    public BasicResponseDto setUserInfo(@RequestBody @Validated UserInfoRequestDto userInfoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.setUserInfo(userDetails.getUser(), userInfoRequestDto);
    }
    @GetMapping("/mypost")
    public List<MyPostDto> myPost(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return userService.myPost(userDetails);
    }

    @GetMapping("/user/profile")
    public UserResponseDto getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("user = {}", userDetails.getUser().getNickName());
        return userService.userprofile(userDetails.getUser());
    }


    @GetMapping("/myanswer")
    public List<MyAnswerDto> myAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return userService.myAnswer(userDetails);
    }

    @GetMapping("/achievement")
    public MyAchievementDto myAchievement(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return userService.myAchievement(userDetails);
    }

    @GetMapping("/mybanner")
    public MyBannerDto myBanner(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return userService.myBanner(userDetails);
    }
    @GetMapping("/error")
    public ResponseEntity<ErrorResponse> error(){
        return new ResponseEntity<>(new ErrorResponse("400", "로그인 정보가 없습니다."), HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/mycount")
    public MyCountDto mycount(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return userService.mycount(userDetails);
    }

}
