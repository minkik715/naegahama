package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerLikeRepository extends JpaRepository<AnswerLike, Long> {
    Long countByAnswer(Answer answer);
    void deleteByAnswer(Answer answer);
    Optional<AnswerLike> findByUserAndAnswer(User user, Answer answer);

}
