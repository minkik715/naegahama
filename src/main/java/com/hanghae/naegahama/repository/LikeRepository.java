package com.hanghae.naegahama.repository;


import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Like;
import com.hanghae.naegahama.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>
{
    Long countByAnswer(Answer answer);
}
