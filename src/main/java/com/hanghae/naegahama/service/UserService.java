package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.config.jwt.JwtAuthenticationProvider;
import com.hanghae.naegahama.domain.Achievement;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.MyPage.MyAchievementDto;
import com.hanghae.naegahama.dto.MyPage.MyBannerDto;
import com.hanghae.naegahama.dto.login.LoginRequestDto;
import com.hanghae.naegahama.dto.MyPage.MyAnswerDto;
import com.hanghae.naegahama.dto.MyPage.MyPostDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.handler.ex.EmailNotFoundException;
import com.hanghae.naegahama.handler.ex.PasswordCheckFailException;
import com.hanghae.naegahama.handler.ex.PasswordNotCollectException;
import com.hanghae.naegahama.kakaologin.KakaoOAuth2;
import com.hanghae.naegahama.kakaologin.KakaoUserInfo;
import com.hanghae.naegahama.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final KakaoOAuth2 kakaoOAuth2;

    //
    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final PostLikeRepository postLikeRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final CommentRepository commentRepository;
    private final AchievementRepository achievementRepository;
    


    public LoginRequestDto signUp(SignUpRequestDto signUpRequestDto) {
        String password = signUpRequestDto.getPassword();
        checkPassword(signUpRequestDto, password);
        User user = new User(signUpRequestDto,encodePassword(password));
        userRepository.save(user);

        // 회원 가입시 업적 리포지토리 저장
        Achievement achievement = new Achievement(user);
        achievementRepository.save(achievement);
        user.setAchievement(achievement);

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
        String token = jwtAuthenticationProvider.createToken(String.valueOf(user.getId()));
        Cookie cookie = new Cookie("access-token",token);
        response.addCookie(cookie);
        return ResponseEntity.ok().body(sendToken(user));
    }

    public ResponseEntity<?> login(String kakaoAccessToken) throws EmailNotFoundException {
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(kakaoAccessToken);
        User user = new User(userInfo);
        userRepository.save(user);

        Achievement achievement = new Achievement(user);
        achievementRepository.save(achievement);
        user.setAchievement(achievement);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    private String sendToken(User user) {
        return jwtAuthenticationProvider.createToken(String.valueOf(user.getId()));
    }

    private void loginPassword(String password, User user) {
        if(passwordEncoder.matches(password, user.getPassword())){
            return;
        }
        throw new PasswordNotCollectException("비밀번호를 확인해주세요.");
    }

    public ResponseEntity<?> emailCheck(String email)
    {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
          return ResponseEntity.ok().body(new BasicResponseDto("false"));
        }
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public List<MyPostDto> myPost(UserDetailsImpl userDetails)
    {
        List<MyPostDto> myPageDtoList = new ArrayList<>();

        User user = userDetails.getUser();

        List<Post> postList = postRepository.findAllByUserOrderByModifiedAtDesc(user);

        for ( Post post : postList)
        {
            Long likeCount = postLikeRepository.countByPost(post);
            MyPostDto postMyPageDto = new MyPostDto(post,likeCount);
            myPageDtoList.add(postMyPageDto);
        }

        return myPageDtoList;
    }

    public List<MyAnswerDto> myAnswer(UserDetailsImpl userDetails)
    {
        List<MyAnswerDto> myAnswerDtoList = new ArrayList<>();
        User user = userDetails.getUser();

        List<Answer> answerList = answerRepository.findAllByUserOrderByModifiedAtDesc(user);
        for ( Answer answer : answerList)
        {
            Long likeCount = answerLikeRepository.countByAnswer(answer);
            MyAnswerDto myAnswerDto = new MyAnswerDto(answer, likeCount);
            myAnswerDtoList.add(myAnswerDto);
        }
        return myAnswerDtoList;
    }


    public MyAchievementDto myAchievement(UserDetailsImpl userDetails)
    {
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
        myAchievementDto.getAchievement()[8] = achievement.getAchievement9();

        // 업적 1 : answerService.answerStar
        // 업적 2 : answerService.answerStar
        // 업적 3 :
        // 업적 4 : commentService.writeComment
        // 업적 5 : postService.createPost
        // 업적 6 :
        // 업적 7 : answerService.answerStar
        // 업적 8 :
        // 업적 9 : answerService.answerWrite;



        return myAchievementDto;
    }

    public MyBannerDto myBanner(UserDetailsImpl userDetails)
    {
        User user = userDetails.getUser();

        return new MyBannerDto(user);
    }


}
