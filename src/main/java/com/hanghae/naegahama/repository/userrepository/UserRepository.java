package com.hanghae.naegahama.repository.userrepository;

import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   // Optional<User> findByNickName(String nickname);
    //Optional<User> findByKakaoId(Long KakaoId);
    //List<User> findTop5ByOrderByPointDesc();

    Optional<User> findByEmail(String email);




}
