package com.hanghae.naegahama.service;

import com.hanghae.naegahama.alarm.*;
import com.hanghae.naegahama.domain.Achievement;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.MyPage.*;
import com.hanghae.naegahama.dto.login.LoginRequestDto;
import com.hanghae.naegahama.dto.MyPage.MyAnswerDto;
import com.hanghae.naegahama.dto.MyPage.MyPostDto;
import com.hanghae.naegahama.dto.login.UserResponseDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.dto.user.UserInfoRequestDto;
import com.hanghae.naegahama.handler.ex.PasswordCheckFailException;
import com.hanghae.naegahama.handler.ex.PasswordNotCollectException;
import com.hanghae.naegahama.initial.Category;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final AchievementRepository achievementRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;



    public LoginRequestDto signUp(SignUpRequestDto signUpRequestDto) {
        String password = signUpRequestDto.getPassword();
        checkPassword(signUpRequestDto, password);
        User user = new User(signUpRequestDto, encodePassword(password));
        userRepository.save(user);

        // 회원 가입시 업적 리포지토리 저장
        Achievement achievement = new Achievement(user);
        achievementRepository.save(achievement);
        user.setAchievement(achievement);

        return new LoginRequestDto(signUpRequestDto.getEmail(), password);

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkPassword(SignUpRequestDto signUpRequestDto, String password) {
        if (password.equals(signUpRequestDto.getPasswordCheck())) {
            return;
        }
        throw new PasswordCheckFailException("비밀번호가 일치하지 않습니다.");
    }

 /*   public ResponseEntity<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) throws EmailNotFoundException {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("해당 이메일은 존재하지 않습니다.")
        );
        loginPassword(password, user);
        return getLoginResponseDtoResponseEntity(user);
    }*/

  /*  @Transactional
    public ResponseEntity<?> kakaoSignup(String kakaoAccessToken) throws EmailNotFoundException, JsonProcessingException {
        log.info("kakaoAccessToken ={}", kakaoAccessToken);
        KakaoUserInfoDto userInfo = kakaoOAuth2.getUserInfo(kakaoAccessToken);
        log.info("kakaoId = {}, nickname = {}", userInfo.getId(), userInfo.getNickname());
        User user = userRepository.findByKakaoId(userInfo.getId()).orElse(null);
        User saveUser;
        if (user == null) {
            User newUser = new User(userInfo);
            log.info("kakaoId = {}", userInfo.getId());
            saveUser = userRepository.save(newUser);
            achievementRepository.save(new Achievement(saveUser));
        }else{
            saveUser = user;
        }
        return getLoginResponseDtoResponseEntity(saveUser);
    }*/

 /*   private ResponseEntity<?> getLoginResponseDtoResponseEntity(User user) {
        String token = jwtAuthenticationProvider.createToken(String.valueOf(user.getId()));
        LoginResponseDto loginResponseDto = new LoginResponseDto(token, user.getId(),user.getUserStatus());
        return ResponseEntity.ok().body(loginResponseDto);
    }*/


    private void loginPassword(String password, User user) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            return;
        }
        throw new PasswordNotCollectException("비밀번호를 확인해주세요.");
    }

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
            MyPostDto postMyPageDto = new MyPostDto(post, user);
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

            MyAnswerDto myAnswerDto = new MyAnswerDto(answer, user);
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

       /* Long cook = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "cook", 4);
        Long health = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "health", 4);
        Long knowledge = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "knowledge", 4);
        Long create = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "create", 4);
        Long visit = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "visit", 4);
        Long job = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "job", 4);
        Long pet = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "pet", 4);
        Long fashion = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "fashion", 4);
        Long consult = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "consult", 4);
        Long device = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "device", 4);
        Long life = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "life", 4);
        Long etc = answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(user, "etc", 4);
        if(cook >= 5){expert[0] = 1;}
        if(health >= 5){expert[1] = 1;}
        if(knowledge >= 5){expert[2] = 1;}
        if(create >= 5){expert[3] = 1;}
        if(visit >= 5){expert[4] = 1;}
        if(job >= 5){expert[5] = 1;}
        if(pet >= 5){expert[6] = 1;}
        if(fashion >= 5){expert[7] = 1;}
        if(consult >= 5){expert[8] = 1;}
        if(device >= 5){expert[9] = 1;}
        if(life >= 5){expert[10] = 1;}
        if(etc >= 5){expert[11] = 1;}
*/
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