package com.hanghae.naegahama.repository.commentrepository;

import com.hanghae.naegahama.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    //int countByAnswer(Answer answer);

    //List<Comment> findAllByParentCommentId(Long id);

    //Long countByParentCommentId(Long id);

}
