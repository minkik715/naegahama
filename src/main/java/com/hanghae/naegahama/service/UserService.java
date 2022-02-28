package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.jwt.JwtAuthenticationProvider;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.login.LoginRequestDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.handler.ex.EmailNotFoundException;
import com.hanghae.naegahama.handler.ex.PasswordCheckFailException;
import com.hanghae.naegahama.handler.ex.PasswordNotCollectException;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public LoginRequestDto signUp(SignUpRequestDto signUpRequestDto) {
        String password = signUpRequestDto.getPassword();
        checkPassword(signUpRequestDto, password);
        User user = new User(signUpRequestDto,encodePassword(password));
        userRepository.save(user);
        return new LoginRequestDto(signUpRequestDto.getEmail(),password);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkPassword(SignUpRequestDto signUpRequestDto, String password) {
        if(password.equals(signUpRequestDto.getPasswordCheck())){
            return;
        }
        throw new PasswordCheckFailException("비밀번호가 일치하지 않습니다.");
    }

    public ResponseEntity<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) throws EmailNotFoundException {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("해당 이메일은 존재하지 않습니다.")
        );
        loginPassword(password, user);
        sendToken(response, user);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    private void sendToken(HttpServletResponse response, User user) {
        String token = jwtAuthenticationProvider.createToken(String.valueOf(user.getId()));
        Cookie cookie =new Cookie("token", token);
        response.addCookie(cookie);
    }

    private void loginPassword(String password, User user) {
        if(passwordEncoder.matches(password, user.getPassword())){
            return;
        }
        throw new PasswordNotCollectException("비밀번호를 확인해주세요.");
    }
}