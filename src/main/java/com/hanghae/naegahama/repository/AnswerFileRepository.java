package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerFileRepository extends JpaRepository<AnswerFile,Long> {
    void deleteByAnswer(Answer answer);


    List<AnswerFile> findAllByAnswerOrderByCreatedAt(Answer answer);
}
