package com.hanghae.naegahama.repository.searcgrepository;

import com.hanghae.naegahama.domain.Search;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

public interface SearchRepository extends JpaRepository<Search, Long> {

    //boolean existsBySearchWordAndUser(String searchWord, User user);

    //List<Search> findAllByUserOrderByCreatedAtDesc(User user);

    void deleteByUser(User user);

    void deleteByIdAndUser(Long searchId, User user);
}
