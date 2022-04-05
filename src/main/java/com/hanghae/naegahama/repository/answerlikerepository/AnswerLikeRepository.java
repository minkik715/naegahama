package com.hanghae.naegahama.repository.answerlikerepository;

import com.hanghae.naegahama.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerLikeRepository extends JpaRepository<AnswerLike, Long> {
    int countByAnswer(Answer answer);
    Optional<AnswerLike> findByUserAndAnswer(User user, Answer answer);

}
