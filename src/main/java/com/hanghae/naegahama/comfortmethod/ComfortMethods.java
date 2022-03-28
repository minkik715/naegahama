package com.hanghae.naegahama.comfortmethod;

import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.ex.LoginUserNotFoundException;
import com.hanghae.naegahama.repository.UserRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComfortMethods {

    private static UserRepository userRepository;

    @Autowired
    private ComfortMethods(UserRepository userRepository){
        ComfortMethods.userRepository = userRepository;
    }

    public static User getUser(UserDetailsImpl userDetails) {
        return userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new LoginUserNotFoundException("유저를 찾을 수 없습니다."));
    }

}
