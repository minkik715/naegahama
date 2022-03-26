package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.domain.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCommentRepository extends JpaRepository<UserComment, Long>
{


}
