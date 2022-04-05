package com.hanghae.naegahama.repository.commentrepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
=======
import com.hanghae.naegahama.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

public interface CommentRepository extends JpaRepository<Comment, Long> {


    //int countByAnswer(Answer answer);

    //List<Comment> findAllByParentCommentId(Long id);

    //Long countByParentCommentId(Long id);

}
