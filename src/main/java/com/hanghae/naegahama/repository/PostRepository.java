package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>
{
    Post findPostById(Long id);
}
