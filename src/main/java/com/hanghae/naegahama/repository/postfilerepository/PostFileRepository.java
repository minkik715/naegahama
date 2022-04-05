package com.hanghae.naegahama.repository.postfilerepository;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostFileRepository extends JpaRepository<PostFile,Long> {
    void deleteByPost(Post answer);
    //List<PostFile> findAllByPostOrderByCreatedAt(Post post);
}
