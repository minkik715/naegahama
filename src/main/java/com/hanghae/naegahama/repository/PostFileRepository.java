package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerFileRepository extends JpaRepository<AnswerFile,Long> {
    void deleteByAnswer(Answer answer);
}
