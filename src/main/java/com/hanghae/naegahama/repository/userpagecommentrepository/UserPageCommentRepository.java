package com.hanghae.naegahama.repository.userpagecommentrepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.domain.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

=======
import com.hanghae.naegahama.domain.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
public interface UserPageCommentRepository extends JpaRepository<UserComment, Long> {

   // List<UserComment> findByPageUserOrderByModifiedAt(User user);

//    List<UserComment> findByParentCommentIdOrderByModifiedAt(Long parentCommentId);
}
