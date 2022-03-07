package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerFile;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostFileRepository extends JpaRepository<PostFile,Long> {
    void deleteByPost(Post answer);
    List<PostFile> findAllByPostOrderByCreatedAt(Post post);
}
