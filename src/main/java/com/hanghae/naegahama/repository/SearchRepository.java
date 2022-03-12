package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Search;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SearchRepository extends JpaRepository<Search, Long> {
<<<<<<< HEAD
    boolean existsBySearchWordAndUser(String searchWord, User user);
=======
    List<Search> findAllByUserOrderByCreatedAtDesc(User user);
    Search findByUserOrderByCreatedAtDesc(User user);
    void deleteByUser(User user);

    boolean existsBySearchWordAndUser(String searchWord, User user);

    void deleteByIdAndUser(Long searchId, User user);
>>>>>>> ef4fc9e08b3164471a9f9f69999d407b72cc335b
}
