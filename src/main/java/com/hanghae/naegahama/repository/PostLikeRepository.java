package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    Long countByPost(Post post);

    List<PostLike> findAllByPost(Post post);
    void deleteByPost(Post post);
}
