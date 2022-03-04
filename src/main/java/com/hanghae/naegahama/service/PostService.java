package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.category.CategoryResponseDto;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PostResponseDto;
import com.hanghae.naegahama.dto.post.ResponseDto;
import com.hanghae.naegahama.handler.ex.PostNotFoundException;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final PostLikeRepository postLikeRepository;
    private final RoomService roomService;
    private final S3Uploader s3Uploader;

    private final PostFileRepository postFileRepository;

    //요청글 작성
    @Transactional
    public ResponseEntity<?> createPost(List<MultipartFile> multipartFileList, PostRequestDto postRequestDto, User user) throws IOException {

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


        Post post = new Post(postRequestDto, user);

        //저장된 Answer을 꺼내와서
        Post savePost = postRepository.save(post);
        //들어온 파일들을 하나씩 처리하는데
        for (MultipartFile file : multipartFileList) {
            String url = s3Uploader.upload(file, "static");
            //S3에서 받아온 URL을 통해서 file을 만들고
            PostFile fileUrl = new PostFile(url);

            //파일에 아까 저장한 Answer를 Set한 후에
            fileUrl.setAnswer(savePost);
            //저장된 파일을 Answer에 넣어준다
            PostFile saveFile = postFileRepository.save(fileUrl);
            savePost.getFileList().add(saveFile);
        }
        //요청글이 생길때 채팅방도 하나 생기게 된다.
        return roomService.createRoom(savePost.getTitle(), post);


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


    //요청글 전체 조회

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
    public ResponseDto getPost1(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("해당 글은 존재하지 않습니다.")
        );

        List<PostLike> likeLIst = postLikeRepository.findAllByPost(post);

        List<Long> userIdList = new ArrayList<>();
        for (PostLike postLike : likeLIst) {
            userIdList.add(postLike.getUser().getId());
        }


        Integer answerCount = answerRepository.countByPost(post);
        Long postLikeCount = postLikeRepository.countByPost(post);
        List<PostFile> findPostFileList = postFileRepository.findAllByPostOrderByCreatedAt(post);
        List<String> fileList = new ArrayList<>();
        for (PostFile postFile : findPostFileList) {
            fileList.add(postFile.getUrl());
        }
        Long roomId = post.getRoom().getId();


        ResponseDto ResponseDto = new ResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getModifiedAt(),
                answerCount,
                post.getUser().getId(),
                post.getUser().getNickName(),
                postLikeCount,
                userIdList,
                fileList,
                post.getLevel(),
                roomId);

        return ResponseDto;
    }

    //카테고리

    public List<CategoryResponseDto> getCategory(String category) {
        List<Post> posts;
        if (category.equals("all")) {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            posts = postRepository.findAllByCategoryOrderByCreatedAtDesc(category);
        }
        List<CategoryResponseDto> response = new ArrayList<>();
        if (posts == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
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
