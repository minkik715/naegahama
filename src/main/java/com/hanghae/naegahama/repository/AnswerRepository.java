package com.hanghae.naegahama.repository;


import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByPostIdOrderByCreatedAt(Long id);
    Integer countByPost(Post post);
    List<Answer> findAllByUserOrderByCreatedAtDesc ( User user);
}
