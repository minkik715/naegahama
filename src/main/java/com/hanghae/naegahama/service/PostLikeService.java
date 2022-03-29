package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.handler.event.PostLikeEvent;
import com.hanghae.naegahama.dto.postlike.PostLikeRequestDto;
import com.hanghae.naegahama.dto.postlike.PostLikeResponseDto;
import com.hanghae.naegahama.repository.AlarmRepository;
import com.hanghae.naegahama.repository.PostLikeRepository;
import com.hanghae.naegahama.repository.PostRepository;
import com.hanghae.naegahama.repository.UserRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public PostLikeResponseDto PostLike(Long postId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("게시글이 없습니다.")
        );

        User postWriter = post.getUser();

        PostLike findPostLike = postLikeRepository.findByUserAndPost(user,post).orElse(null);
        log.info("userId ={}", user.getId());
        if(findPostLike == null){
            postLikeRepository.save(new PostLike(new PostLikeRequestDto(user, post)));
            applicationEventPublisher.publishEvent(new PostLikeEvent(postWriter,user,post));
        } else
        {
            postLikeRepository.deleteById(findPostLike.getId());
            if(!postWriter.getNickName().equals(user.getNickName())) {
                postWriter.setPoint(postWriter.getPoint() - 25);
            }
        }
        return new PostLikeResponseDto(postId, postLikeRepository.countByPost(post));

    }

}
