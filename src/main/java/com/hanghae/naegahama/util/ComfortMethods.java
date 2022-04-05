package com.hanghae.naegahama.util;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.ex.AnswerNotFoundException;
import com.hanghae.naegahama.ex.CommentNotFoundException;
import com.hanghae.naegahama.ex.LoginUserNotFoundException;
import com.hanghae.naegahama.repository.answerrepository.AnswerRepository;
import com.hanghae.naegahama.repository.commentrepository.CommentRepository;
import com.hanghae.naegahama.repository.postrepository.PostRepository;
import com.hanghae.naegahama.repository.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComfortMethods {

    private static UserRepository userRepository;
    private static AnswerRepository answerRepository;
    private static PostRepository postRepository;
    private static CommentRepository commentRepository;

    @Autowired
    private ComfortMethods(UserRepository userRepository, AnswerRepository answerRepository, PostRepository postRepository, CommentRepository commentRepository){
        ComfortMethods.userRepository = userRepository;
        ComfortMethods.answerRepository = answerRepository;
        ComfortMethods.postRepository = postRepository;
        ComfortMethods.commentRepository = commentRepository;
    }

    public static User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new LoginUserNotFoundException("유저를 찾을 수 없습니다."));
    }
    public static Answer getAnswer(Long answerId){
        return answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글은 존재하지 않습니다."));
    }

    public static Post getPost(Long postId){
        return postRepository.findById(postId).orElseThrow(
                () -> new AnswerNotFoundException("해당 요청글은 존재하지 않습니다."));
    }

    public static Comment getComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글은 존재하지 않습니다."));
    }

}
