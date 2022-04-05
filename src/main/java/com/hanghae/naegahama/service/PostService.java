package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.post.*;
import com.hanghae.naegahama.event.PostClosedEvent;
import com.hanghae.naegahama.event.PostWriteEvent;
import com.hanghae.naegahama.ex.*;
import com.hanghae.naegahama.repository.postfilerepository.PostFileQuerydslRepository;
import com.hanghae.naegahama.repository.postfilerepository.PostFileRepository;
import com.hanghae.naegahama.repository.postlikerepository.PostLikeQuerydslRepository;
import com.hanghae.naegahama.repository.postrepository.PostQuerydslRepository;
import com.hanghae.naegahama.repository.postrepository.PostRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeQuerydslRepository postLikeQuerydslRepository;
    private final PostFileQuerydslRepository postFileQuerydslRepository;
    private final PostFileRepository postFileRepository;

    private final PostQuerydslRepository postQuerydslRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    //요청글 작성
    @Transactional
    public Long createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Post post = PostWrite(postRequestDto, user);
        applicationEventPublisher.publishEvent(new PostWriteEvent(post));
        return post.getId();
    }


    //요청글 수정
    @Transactional
    public BasicResponseDto updatePost(Long id, PutRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        User user = post.getUser();

        if (!userDetails.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        post.UpdatePost(postRequestDto);

        postFileRepository.deleteByPost(post);

        for (String url : postRequestDto.getFile()) {
            PostFile fileUrl = new PostFile(url,post);
            postFileRepository.save(fileUrl);
        }

        return new BasicResponseDto("success");
    }


    public List<PostResponseDto> getPost() {
       return createPostResponseDto(postQuerydslRepository.findPosts());
    }


    //요청글 상세조회.
    public ResponseDto getPost1(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("해당 글은 존재하지 않습니다.")
        );

        List<Long> userIdList = new ArrayList<>();
        for (PostLike postLike : post.getPostLikes()) {
            userIdList.add(postLike.getUser().getId());
        }

        Integer answerCount = post.getAnswerList().size();
        Long postLikeCount = postLikeQuerydslRepository.countPostLikes(post.getId());
        List<PostFile> findPostFileList = postFileQuerydslRepository.findFileByPost(post.getId());
        List<String> fileList = new ArrayList<>();
        for (PostFile postFile : findPostFileList) {
            fileList.add(postFile.getUrl());
        }
        getDeadLine(post);

        ResponseDto ResponseDto = new ResponseDto(
                post,
                answerCount,
                postLikeCount,
                userIdList,
                fileList,
                getDeadLine(post));
        return ResponseDto;
    }

    //카테고리

    public List<PostResponseDto> getCategory(String category) {
        List<Post> posts;
        if (category.equals("all")) {
            return createPostResponseDto(postQuerydslRepository.findPosts());
        } else {
            return createPostResponseDto(postQuerydslRepository.findPostsByCategory(category));
        }
    }

    public List<PostResponseDto>  getPostByCategoryAndSort(String category, String sort) {

        Comparator<Post> comparator = new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {

                if (o1.getDeadLine().isBefore(o2.getDeadLine())) {
                    return -1;
                } else if (!o1.getDeadLine().isBefore(o2.getDeadLine())) {
                    return 1;
                } else {
                    return 0;
                }


            }
        };

        List<Post> posts;
        List<PostResponseDto> response = new ArrayList<>();
        List<PostResponseDto> tempresponse = new ArrayList<>();
        List<PostResponseDto> tempresponse2 = new ArrayList<>();
        if (category.equals("all")) {
            posts =  postQuerydslRepository.findPosts();
        } else {
            posts = postQuerydslRepository.findPostsByCategory(category);
        }
        if (posts == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
        if (sort.equals("like")) {
            Collections.sort(posts);
            response = createPostResponseDto(posts);
        } else if (sort.equals("time")) {
            Collections.sort(posts, comparator);
            for (Post post : posts) {
                int year = post.getDeadLine().getYear();
                if (year != 2100 && post.getStatus().equals("opened")) { //마감X 기한없음X
                    createPostResponseDto(response, post);
                } else if (year == 2100 && post.getStatus().equals("opened")) { //마감X 기한없음ㅇ
                    createPostResponseDto(tempresponse, post);
                }else{
                    createPostResponseDto(tempresponse2, post);
                }
            }

        } else {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }


        response.addAll(tempresponse);
        response.addAll(tempresponse2);
        return response;
    }

    private void createPostResponseDto(List<PostResponseDto> response, Post post) {
        response.add(new PostResponseDto(post,getDeadLine(post)));
    }

    private List<PostResponseDto> createPostResponseDto(List<Post> posts) {
        return posts.stream().
                map(p -> new PostResponseDto(p,getDeadLine(p)))
                .collect(Collectors.toList());
    }


    private String getDeadLine(Post post) {
        String timeSet;
        LocalDateTime deadLine = post.getDeadLine();
        if (deadLine.getYear() == 2100) {
            timeSet = "기한 없음";
        } else {
            if (deadLine.isBefore(LocalDateTime.now())) {
                post.setStatus("closed");
                timeSet = "마감";
            } else {
                long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), deadLine);
                if(minutes > 60){
                    long hour = minutes/60;
                    minutes = minutes%60;
                    timeSet = hour+":"+minutes+"남음";
                }else{
                    timeSet = "00:"+minutes+"남음";
                }
            }
        }
        return timeSet;
    }

    @Transactional
    public BasicResponseDto finishPost(Long postId, User user) {
        Post findPost = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("존재하지 않는 글 입니다.")
        );
        if (!findPost.getUser().getId().equals(user.getId())) {
            throw new UserNotFoundException("권한이 존재하지 않습니다");
        }
        findPost.setStatus("closed");
        if(!(findPost.getAnswerList().size() ==0)){
            applicationEventPublisher.publishEvent(new PostClosedEvent(findPost.getUser(),findPost));
        }
        return new BasicResponseDto("success");
    }


    // Post 작성 json 데이터 예외처리




    // @Transactional을 사용하기 위해 userRepository에서 불러온 유저를 반환



    // Post 작성 및 저장
    Post PostWrite(PostRequestDto postRequestDto, User user) {
        // 파라미터 값을 통해 post 기본 칼럼 ( 제목, 내용, 범주, 난이도 ) 적용 후 생성 및 저장

        Post post = postRepository.save(new Post(postRequestDto, user));

        for (String url : postRequestDto.getFile()) {
            postFileRepository.save(new PostFile(url, post));
        }
        // 잔여시간 처리
        if (postRequestDto.getTimeSet() == 0) {
            post.setDeadLine(LocalDateTime.of(2100, 7, 15, 0, 0, 0));
        } else {
            LocalDateTime deadline = post.getCreatedAt().plusHours(postRequestDto.getTimeSet());
            post.setDeadLine(deadline);
        }
        return post;
    }




    public List<PostResponseDto> getAdminPost() {
        List<PostResponseDto> response = new ArrayList<>();

        for (Post post : postQuerydslRepository.findAdminPost(UserRoleEnum.ADMIN)) {
            createPostResponseDto(response,post);
        }

        return response;
    }


}


