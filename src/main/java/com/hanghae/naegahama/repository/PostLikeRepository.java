package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    void deleteByPost(Post post);
}
