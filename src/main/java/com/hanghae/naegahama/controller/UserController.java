package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.login.KakaoLoginRequestDto;
import com.hanghae.naegahama.dto.login.LoginRequestDto;
import com.hanghae.naegahama.dto.post.PostMyPageDto;
import com.hanghae.naegahama.dto.signup.EmailDuplCheckDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto, HttpServletResponse response){
        return(login(userService.signUp(signUpRequestDto), response));
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto,HttpServletResponse httpServletResponse){
        return userService.login(loginRequestDto,httpServletResponse);
    }

    @PostMapping("/user/idcheck")
    public ResponseEntity<?> emailCheck(@RequestBody EmailDuplCheckDto emailDuplCheckDto){
        return userService.emailCheck(emailDuplCheckDto.getEmail());
    }

    @PostMapping("/user/kakaoLogin")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> param){
        return userService.login(param.get("kakaoToken").toString());
    }

    @GetMapping("/user")
    public ResponseEntity<?> login(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok().body(new BasicResponseDto(userDetails.getUser().getNickName()));
    }

    @GetMapping("/mypost")
    public List<PostMyPageDto> myWrite(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return userService.myWrite(userDetails);
    }

}
