package com.hanghae.naegahama.repository;

<<<<<<< HEAD

import com.hanghae.naegahama.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
=======
import com.hanghae.naegahama.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>
{
    List<Answer> findAllByPostId(Long id);
>>>>>>> f4f2f39d602a5dcbdf310bf85927e7b15e8c3c63
}
