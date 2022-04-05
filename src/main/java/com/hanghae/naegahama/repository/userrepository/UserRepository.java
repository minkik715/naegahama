package com.hanghae.naegahama.repository.userrepository;

import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   // Optional<User> findByNickName(String nickname);
    //Optional<User> findByKakaoId(Long KakaoId);
    //List<User> findTop5ByOrderByPointDesc();

    Optional<User> findByEmail(String email);




}
