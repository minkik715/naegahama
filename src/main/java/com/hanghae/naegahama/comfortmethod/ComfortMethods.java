package com.hanghae.naegahama.comfortmethod;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.ex.AnswerNotFoundException;
import com.hanghae.naegahama.ex.LoginUserNotFoundException;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.UserRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComfortMethods {

    private static UserRepository userRepository;
    private static AnswerRepository answerRepository;

    @Autowired
    private ComfortMethods(UserRepository userRepository, AnswerRepository answerRepository){
        ComfortMethods.userRepository = userRepository;
        ComfortMethods.answerRepository = answerRepository;
    }

    public static User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new LoginUserNotFoundException("유저를 찾을 수 없습니다."));
    }
    public static Answer getAnswer(Long answerId){
        return answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글은 존재하지 않습니다."));
    }

}
