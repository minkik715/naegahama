package com.hanghae.naegahama.repository.postlikerepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
=======
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    Long countByPost(Post post);

}
