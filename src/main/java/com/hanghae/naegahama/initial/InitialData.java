package com.hanghae.naegahama.initial;

import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.repository.PostRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialData implements ApplicationRunner {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> UserList = new ArrayList<>();
        UserList.add(new User("aaa@gmail.com", "aaa", passwordEncoder.encode("123456"), 1000));
        UserList.add(new User("bbb@gmail.com", "bbb", passwordEncoder.encode("123456"), 2000));
        UserList.add(new User("ccc@gmail.com", "ccc", passwordEncoder.encode("123456"), 3000));
        UserList.add(new User("ddd@gmail.com", "ddd", passwordEncoder.encode("123456"), 4000));
        UserList.add(new User("eee@gmail.com", "eee", passwordEncoder.encode("123456"), 5000));
        UserList.add(new User("fff@gmail.com", "fff", passwordEncoder.encode("123456"), 6000));
        UserList.add(new User("ggg@gmail.com", "ggg", passwordEncoder.encode("123456"), 7000));
        userRepository.saveAll(UserList);




    }

}