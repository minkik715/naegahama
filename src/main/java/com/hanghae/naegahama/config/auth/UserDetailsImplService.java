package com.hanghae.naegahama.config.auth;

import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.handler.ex.EmailNotFoundException;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsImplService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User findUser = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new EmailNotFoundException("가입되지 않은 이메일입니다.")
        );

        return new UserDetailsImpl(findUser);
    }
}