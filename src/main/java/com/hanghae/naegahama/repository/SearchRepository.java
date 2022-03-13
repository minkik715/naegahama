package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Search;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SearchRepository extends JpaRepository<Search, Long> {

    List<Search> findAllByUserOrderByCreatedAtDesc(User user);
    Search findByUserOrderByCreatedAtDesc(User user);
    void deleteByUser(User user);

    boolean existsBySearchWordAndUser(String searchWord, User user);

    void deleteByIdAndUser(Long searchId, User user);

}
