package com.hanghae.naegahama.repository;


import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByPostId(Long id);
    Integer countByPost(Post post);
}
