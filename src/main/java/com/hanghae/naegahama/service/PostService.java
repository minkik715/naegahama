package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.category.CategoryResponseDto;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PostResponseDto;
import com.hanghae.naegahama.dto.post.ResponseDto;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import com.hanghae.naegahama.repository.PostLikeRepository;
import com.hanghae.naegahama.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final PostLikeRepository postLikeRepository;

    //요청글 작성
    @Transactional
    public Post createPost(PostRequestDto postRequestDto, User user) {

        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
        String content = postRequestDto.getContent();
        if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
        if (content.length() > 1000) {
            throw new IllegalArgumentException("1000자 이하로 입력해주세요.");
        }

        log.info("level ={}", postRequestDto.getLevel());
        Post post = new Post(postRequestDto, user);
        log.info("level ={}", post.getLevel());
        return postRepository.save(post);
    }


    //요청글 수정
    @Transactional
    public Post updatePost(
            Long id,
            PostRequestDto postRequestDto,
            UserDetailsImpl userDetails) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        User user = post.getUser();
        if (userDetails.getUser() != user) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
        if (postRequestDto.getContent().length() > 1000) {
            throw new IllegalArgumentException("1000자 이하로 입력해주세요.");
        }
        post.UpdatePost(postRequestDto, userDetails.getUser());
        postRepository.save(post);
        return post;
    }

    //요청글 삭제
    @Transactional
    public Post deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        User user = post.getUser();
        Long deleteId = user.getId();
        if (userDetails.getUser().getId() == deleteId) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }


        List<Comment> comments = commentRepository.findAllByAnswer(null);
        for (Comment comment : comments) {
            commentRepository.deleteById(comment.getId());
        }
        postLikeRepository.deleteByPost(post);
        postRepository.deleteById(id);
        return post;
    }

    //요청글 전체 조회
    @ResponseBody
    public List<PostResponseDto> getPost() {

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> response = new ArrayList<>();

        for (Post post : posts) {
            Integer answerCount = answerRepository.countByPost(post);
            Long postLikeCount = postLikeRepository.countByPost(post);
            PostResponseDto postResponseDto = new PostResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    answerCount,
                    postLikeCount
            );
            response.add(postResponseDto);
        }
        return response;
    }

    //요청글 상세조회.
    @ResponseBody
    public List<ResponseDto> getPost1(Long postId) {
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(postId);
        List<ResponseDto> response = new ArrayList<>();

        for (Post post : posts) {
            Integer answerCount = answerRepository.countByPost(post);
            Long postLikeCount = postLikeRepository.countByPost(post);
            ResponseDto ResponseDto = new ResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    answerCount,
                    post.getUser().getId(),
                    post.getUser().getNickName(),
                    postLikeCount
            );
            response.add(ResponseDto);
        }
        return response;
    }

    //카테고리
    @ResponseBody
    public List<CategoryResponseDto> getCategory(String category) {

        List<Post> posts = postRepository.findAllByCategoryOrderByCreatedAtDesc(category);
        List<CategoryResponseDto> response = new ArrayList<>();

        for (Post post : posts) {
            Integer answerCount = answerRepository.countByPost(post);
            Long postLikeCount = postLikeRepository.countByPost(post);
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    answerCount,
                    postLikeCount
            );
            response.add(categoryResponseDto);
        }
        return response;
    }
}