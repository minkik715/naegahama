package com.hanghae.naegahama.repository.commentrepository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    //int countByAnswer(Answer answer);

    //List<Comment> findAllByParentCommentId(Long id);

    //Long countByParentCommentId(Long id);

}
