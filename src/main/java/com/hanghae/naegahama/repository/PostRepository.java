package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    Post findPostById(Long id);


}