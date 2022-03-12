package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Search;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SearchRepository extends JpaRepository<Search, Long> {
    boolean existsBySearchWordAndUser(String searchWord, User user);
}
