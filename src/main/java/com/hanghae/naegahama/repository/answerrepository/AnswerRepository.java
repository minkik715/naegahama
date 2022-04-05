package com.hanghae.naegahama.repository.answerrepository;
<<<<<<< HEAD
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
=======

import com.hanghae.naegahama.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

public interface AnswerRepository extends JpaRepository<Answer, Long> {

 /*   @Query("select distinct a from Answer a left join fetch a.user left join fetch a.answerVideo left join fetch a.likeList")
    List<Answer> findAllByPostOrderByCreatedAt(Post post);*/
  //  Integer countByPost(Post post);
    //List<Answer> findAllByUserOrderByModifiedAtDesc ( User user);

    //Long countByUserAndPost_CategoryAndStarGreaterThanEqual(User user,String category,Integer star);


 //   Long countByUser(User user);

    // 검색된 키워드 불러오기.
  //  List<Answer> findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(String searchWord1, String searchWord2);    // 최신순
   // Integer countByContentContainingOrTitleContaining(String searchWord, String searchWord2);
}
