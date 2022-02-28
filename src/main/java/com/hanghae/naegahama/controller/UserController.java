package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.login.KakaoLoginRequestDto;
import com.hanghae.naegahama.dto.login.LoginRequestDto;
import com.hanghae.naegahama.dto.signup.EmailDuplCheckDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto, HttpServletResponse response){
        return(login(userService.signUp(signUpRequestDto), response));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto,HttpServletResponse httpServletResponse){
        return userService.login(loginRequestDto,httpServletResponse);
    }

    @PostMapping("/idcheck")
    public ResponseEntity<?> emailCheck(@RequestBody EmailDuplCheckDto emailDuplCheckDto){
        return userService.emailCheck(emailDuplCheckDto.getEmail());
    }

    @PostMapping("/kakaoLogin")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> param){
        return userService.login(param.get("kakaoToken").toString());
    }

}
