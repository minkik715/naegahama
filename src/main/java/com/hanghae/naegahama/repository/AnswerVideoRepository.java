package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerVideoRepository extends JpaRepository<AnswerVideo,Long> {
    void deleteByAnswer(Answer answer);

    List<AnswerVideo> findAllByAnswerOrderByCreatedAt(Answer answer);
}
