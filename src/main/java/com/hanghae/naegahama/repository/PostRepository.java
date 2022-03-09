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

    List<Post> findAllByCategoryAndStateOrderByTimeSet(String category, String state);          // 잔여시간 순
    // 좋아요 순



    // 검색된 키워드 불러오기.
    List<Post> findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(String searchWord);    // 최신순

    Post findPostById(Long id);
    Long countByPost(Post post);
}
