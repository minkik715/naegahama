package com.hanghae.naegahama.repository.postrepository;

import com.hanghae.naegahama.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAllByOrderByCreatedAtDesc();

//    List<Post> findAllByUserOrderByModifiedAtDesc(User user);

//    List<Post> findAllByUser_HippoName(String hippoName);
//    List<Post> findAllByCategoryOrderByCreatedAtDesc(String category);


    // 작성 완료된 모든 글 불러오기
    //질문
    //    @Query("select distinct p from Post p join fetch p.user join fetch p.answerList")
    // - > answerList를 가져올때 answervideo를 계속해서 가져오게됨
   /* @Query("select distinct p from Post p join fetch p.user join fetch p.answerList as a join fetch a.answerVideo")
    List<Post> findAllByOrderByCreatedAtDesc();*/

    // 작성 완료된 글 카테고리 분야로 보기.
    //List<Post> findAllByCategoryOrderByCreatedAtDesc(String category);    // 최신순

    //List<Post> findAllByUser_RoleOrderByCreatedAtDesc(UserRoleEnum userRoleEnum);

    //Long countByUser(User user);



//    List<Post> findAllByCategoryAndStateOrderByTimeSet(String category, String state);          // 잔여시간 순
//    // 좋아요 순


//    Integer countByContentContainingOrTitleContaining(String searchWord, String searchWord2);

}
