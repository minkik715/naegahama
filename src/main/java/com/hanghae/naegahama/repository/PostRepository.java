package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByUserOrderByModifiedAtDesc(User user);
    List<Post> findAllByUserHippoName(String hippoName);
    List<Post> findAllByCategoryOrderByCreatedAtDesc(String category);

    Post findPostById(Long id);
}
