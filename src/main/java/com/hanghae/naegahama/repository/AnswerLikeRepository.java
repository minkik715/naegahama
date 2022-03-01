package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerLikeRepository extends JpaRepository<PostLike, Long> {
}
