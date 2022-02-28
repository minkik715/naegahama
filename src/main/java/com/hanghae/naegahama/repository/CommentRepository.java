package com.hanghae.naegahama.repository;


import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    Long countByAnswer(Answer answer);
}
