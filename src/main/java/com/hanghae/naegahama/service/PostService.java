package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PostResponseDto;
import com.hanghae.naegahama.dto.post.ResponseDto;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import com.hanghae.naegahama.repository.PostLikeRepository;
import com.hanghae.naegahama.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EnableAutoConfiguration
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public Post createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        List<Answer> answerList1 = new ArrayList<>();
        for (Answer answers : postRequestDto.getAnswerList()) {
            answerList1.add(answerRepository.save(answers)
            );
        }

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

        Post post = new Post(postRequestDto, userDetails, answerList1);

        return postRepository.save(post);
    }

    //수정
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
        post.updatePost(postRequestDto);
        postRepository.save(post);
        return post;
    }

    //삭제
    @Transactional
    public Post deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        User user = post.getUser();
        Long deleteId = user.getId();
        if (!Objects.equals(userDetails.getUser().getId(), deleteId)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        List<Comment> comments = commentRepository.findAllByAnswerId(answer);
        for (Comment comment : comments) {
            commentRepository.deleteById(comment.getId());
        }
        postLikeRepository.deleteByPost(post);
        postRepository.deleteById(postId);
        return postId;
    }


    //전체글 조회
    public List<PostResponseDto> getPost() {

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> response = new ArrayList<>();

        for (Post post : posts) {
            Integer answerCount = answerRepository.countByPost(post);
            PostResponseDto postResponseDto = new PostResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    answerCount
            );
            response.add(postResponseDto);
        }
        return response;
    }





    @ResponseBody
    public List<ResponseDto> getPost1(Long postId, UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(userDetails.getUser());
        List<ResponseDto> response = new ArrayList<>();

        for (Post post : posts) {
            Integer answerCount = answerRepository.countByPost(postId);
            ResponseDto ResponseDto = new ResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    answerCount,
                    post.getUser().getId(),
                    post.getUser().getNickName()
            );
            response.add(ResponseDto);
        }
        return response;
    }
}
