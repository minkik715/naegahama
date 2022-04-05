package com.hanghae.naegahama.repository.postfilerepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerFile;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
public interface PostFileRepository extends JpaRepository<PostFile,Long> {
    void deleteByPost(Post answer);
    //List<PostFile> findAllByPostOrderByCreatedAt(Post post);
}
