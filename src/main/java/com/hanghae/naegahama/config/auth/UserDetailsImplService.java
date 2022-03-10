package com.hanghae.naegahama.config.auth;

import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.handler.ex.EmailNotFoundException;
import com.hanghae.naegahama.handler.ex.ErrorResponse;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class UserDetailsImplService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Transactional(readOnly = true)
    public UserDetails checkUserByUserId(String userIdStr, HttpServletResponse response){
        long userId = Long.parseLong(userIdStr);
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new EmailNotFoundException("가입되지 않은 이메일입니다.")
        );
        return new UserDetailsImpl(findUser);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> EmailNotFoundException(EmailNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}