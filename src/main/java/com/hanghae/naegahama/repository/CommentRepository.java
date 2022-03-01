package com.hanghae.naegahama.repository;


<<<<<<< HEAD
import com.hanghae.naegahama.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
=======
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    Long countByAnswer(Answer answer);
>>>>>>> f4f2f39d602a5dcbdf310bf85927e7b15e8c3c63
}
