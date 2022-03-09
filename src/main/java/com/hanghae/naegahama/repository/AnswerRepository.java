package com.hanghae.naegahama.repository;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerLike;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByPostIdOrderByCreatedAt(Long id);
    Integer countByPost(Post post);
    List<Answer> findAllByUserOrderByModifiedAtDesc ( User user);
    Optional<Answer> findByUserAndStar (User user, Long star);
    List<Answer> findAllByUserAndState(User user, String state);


    // 검색된 키워드 불러오기.
    List<Answer> findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(String searchWord1, String searchWord2);    // 최신순
    Integer countByContentContainingOrTitleContaining(String searchWord, String searchWord2);
}
