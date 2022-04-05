package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.domain.User;
<<<<<<< HEAD
import com.hanghae.naegahama.event.PostLikeEvent;
import com.hanghae.naegahama.dto.postlike.PostLikeRequestDto;
import com.hanghae.naegahama.dto.postlike.PostLikeResponseDto;
=======
import com.hanghae.naegahama.dto.postlike.PostLikeRequestDto;
import com.hanghae.naegahama.dto.postlike.PostLikeResponseDto;
import com.hanghae.naegahama.event.PostLikeEvent;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.repository.postlikerepository.PostLikeQuerydslRepository;
import com.hanghae.naegahama.repository.postlikerepository.PostLikeRepository;
import com.hanghae.naegahama.repository.postrepository.PostRepository;
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
    private final PostLikeQuerydslRepository postLikeQuerydslRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public PostLikeResponseDto PostLike(Long postId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("게시글이 없습니다.")
        );

        User postWriter = post.getUser();

        PostLike findPostLike = postLikeQuerydslRepository.findPostLikeByUserPost(user.getId(),post.getId()).orElse(null);
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
        return new PostLikeResponseDto(postId, postLikeQuerydslRepository.countPostLikes(post.getId()));

    }

}
