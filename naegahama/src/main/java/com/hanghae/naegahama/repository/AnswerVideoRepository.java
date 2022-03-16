package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerFile;
import com.hanghae.naegahama.domain.AnswerVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerVideoRepository extends JpaRepository<AnswerVideo,Long> {
    void deleteByAnswer(Answer answer);
    List<AnswerVideo> findAllByUrlEndingWithOrUrlEndingWithOrderByCreatedAtDesc( String ext, String ext2);

    List<AnswerVideo> findAllByAnswerOrderByCreatedAt(Answer answer);

    Optional<AnswerVideo> findByAnswer(Answer answer);
}
