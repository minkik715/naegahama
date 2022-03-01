package com.hanghae.naegahama.repository;


import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>
{
    Long countByAnswer(Answer answer);
}
