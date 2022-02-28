package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.login.LoginRequestDto;
import com.hanghae.naegahama.dto.signup.EmailDuplCheckDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return(login(userService.signUp(signUpRequestDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }

    @PostMapping("/idcheck")
    public ResponseEntity<?> emailCheck(@RequestBody EmailDuplCheckDto emailDuplCheckDto){
        return userService.emailCheck(emailDuplCheckDto.getEmail());
    }

}
