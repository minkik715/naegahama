package com.hanghae.naegahama.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);

    List<Post> findAllByOrderByCreatedAtDesc();
}
