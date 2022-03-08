package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByUserOrderByModifiedAtDesc(User user);
    List<Post> findAllByUserHippoName(String hippoName);
//    List<Post> findAllByCategoryOrderByCreatedAtDesc(String category);

    // 해당 계정이 작성한 임시작성글 불러오기
    List<Post> findAllByUserAndState(User user, String state);
    // 작성 완료된 모든 글 불러오기
    List<Post> findAllByStateOrderByCreatedAtDesc(String state);
    // 작성 완료된 글 카테고리 분야로 보기.
    List<Post> findAllByCategoryAndStateOrderByCreatedAtDesc(String category, String state);    // 최신순


    Post findPostById(Long id);
}
