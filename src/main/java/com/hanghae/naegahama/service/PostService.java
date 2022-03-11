package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.post.*;
import com.hanghae.naegahama.handler.ex.*;
import com.hanghae.naegahama.repository.*;


import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostFileRepository postFileRepository;

    //요청글 작성
    public ResponseEntity<?> createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails)
    {
        // 로그인 예외 처리 + json 데이터 예외처리
        UserCheck(userDetails);
        PostWriteException(postRequestDto);

        // @Transactional을 사용하기 위해 userRepository에서 불러온 유저를 반환
        User user = GetUser(userDetails);;

        // Post 작성 및 저장
        PostWrite(postRequestDto,user);

        // 최초 요청글 작성 업적 획득
        user.getAchievement().setAchievement5(1);

        // 3, 6번째 요청글 작성 시 50 경험치 획득
        PostWriteAddPoint(user);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }





    //요청글 수정
    @Transactional
    public ResponseEntity<?> updatePost(Long id, PutRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        User user = post.getUser();

        System.out.println(userDetails.getUser().getId());
        System.out.println(user.getId());

        if (!userDetails.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
        if (postRequestDto.getContent().length() > 1000) {
            throw new IllegalArgumentException("1000자 이하로 입력해주세요.");
        }
        post.UpdatePost(postRequestDto);


        // 기존에 있던 이미지 파일 S3에서 삭제
       /* for (PostFile deleteS3 : post.getFileList()) {
            String[] fileKey = deleteS3.getUrl().split("static/");
            try {
                String decodeKey = URLDecoder.decode(fileKey[1], "UTF-8");
                s3Uploader.deleteS3("static/" + decodeKey);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }*/


        // 기존에 있던 포스트파일 제거
        postFileRepository.deleteByPost(post);

        // 새로운 이미지 파일 url 배열로 for 반복문을 실행
        for (String url : postRequestDto.getFile()) {
            // 이미지 파일 url로 postFile 객체 생성
            PostFile fileUrl = new PostFile(url);

            // postFile에 savePost를 연관관계 설정
            fileUrl.setPost(post);

            // 이미지 파일 url 1개에 해당되는 postFile을 DB에 저장
            PostFile saveFile = postFileRepository.save(fileUrl);

            // 저장된 postFile을 저장된 post에 한개씩 추가함
            post.getFileList().add(saveFile);
        }

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

//    //요청글 삭제
//    @Transactional
//    public Post deletePost(Long id, UserDetailsImpl userDetails) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
//        );
//        User user = post.getUser();
//        Long deleteId = user.getId();
//        if (userDetails.getUser().getId() != deleteId) {
//            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
//        }
//
//        List<Comment> comments = commentRepository.findAllByAnswer(null);
//        for (Comment comment : comments) {
//            commentRepository.deleteById(comment.getId());
//        }
//        postLikeRepository.deleteByPost(post);
//        postRepository.deleteById(id);
//        return post;
//    }

    //요청글 전체 조회

    public List<PostResponseDto> getPost() {

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> response = new ArrayList<>();


        for (Post post : posts) {
            Integer answerCount = answerRepository.countByPost(post);
            Long postLikeCount = postLikeRepository.countByPost(post);

            String timeSet = getDeadLine(post);

            PostResponseDto postResponseDto = new PostResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    answerCount,
                    postLikeCount,
                    timeSet,
                    post.getStatus()
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
        getDeadLine(post);

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
                post.getCategory(),
                getDeadLine(post),
                post.getStatus());


        return ResponseDto;
    }

    //카테고리

    public List<PostResponseDto> getCategory(String category) {
        List<Post> posts;
        if (category.equals("all")) {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            posts = postRepository.findAllByCategoryOrderByCreatedAtDesc(category);
        }
        List<PostResponseDto> response = new ArrayList<>();
        if (posts == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
        for (Post post : posts) {
            Integer answerCount = answerRepository.countByPost(post);
            Long postLikeCount = postLikeRepository.countByPost(post);
            PostResponseDto categoryResponseDto = new PostResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    answerCount,
                    postLikeCount,
                    getDeadLine(post),
                    post.getStatus()
            );
            response.add(categoryResponseDto);
        }
        return response;
    }


    /*public ResponseEntity<?> getTimeSet(Long postId) {
        Post post = postRepository.getById(postId);

        GetTimeSetDto getTimeSetDto = new GetTimeSetDto(post);
        return ResponseEntity.ok().body(getTimeSetDto);
    }*/

    public ResponseEntity<?> getPostByCategoryAndSort(String category, String sort) {

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
        if (category.equals("all")) {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            posts = postRepository.findAllByCategoryOrderByCreatedAtDesc(category);
        }
        if (posts == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
        if (sort.equals("like")) {
            Collections.sort(posts);

            for (Post post : posts) {
                Integer answerCount = answerRepository.countByPost(post);
                Long postLikeCount = postLikeRepository.countByPost(post);
                PostResponseDto categoryResponseDto = new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getModifiedAt(),
                        answerCount,
                        postLikeCount,
                        getDeadLine(post),
                        post.getStatus()
                );
                response.add(categoryResponseDto);
            }
        } else if (sort.equals("time")) {
            Collections.sort(posts, comparator);
            for (Post post : posts) {
                if (post.getStatus().equals("true")) {
                    Integer answerCount = answerRepository.countByPost(post);
                    Long postLikeCount = postLikeRepository.countByPost(post);
                    PostResponseDto categoryResponseDto = new PostResponseDto(
                            post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getModifiedAt(),
                            answerCount,
                            postLikeCount,
                            getDeadLine(post),
                            post.getStatus()
                    );
                    response.add(categoryResponseDto);
                } else {
                    Integer answerCount = answerRepository.countByPost(post);
                    Long postLikeCount = postLikeRepository.countByPost(post);
                    PostResponseDto categoryResponseDto = new PostResponseDto(
                            post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getModifiedAt(),
                            answerCount,
                            postLikeCount,
                            getDeadLine(post),
                            post.getStatus()
                    );
                    tempresponse.add(categoryResponseDto);
                }
            }
            response.addAll(tempresponse);

        } else {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }


        response.addAll(tempresponse);
        return ResponseEntity.ok().body(response);
    }


    private String getDeadLine(Post post) {
        String timeSet;
        LocalDateTime deadLine = post.getDeadLine();
        if (deadLine == null) {
            timeSet = "기한 없음";
        } else {
            long minutes = 61;
            if (deadLine.isBefore(LocalDateTime.now())) {
                post.setStatus("false");
                timeSet = "마감된 요청글 입니다.";
            } else {
                minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), deadLine);
                int hour = (int) (minutes / 60);
                minutes = minutes % 60;
                timeSet = "마감 " + hour + "시간 " + minutes + "분 전";

                if (hour < 1) {
                    minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), deadLine);
                    timeSet = "마감 " + minutes + "분 전";

                }
                if (minutes < 1) {
                    long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), deadLine);
                    timeSet = "마감 " + seconds + "초 전";
                }
            }
        }
        return timeSet;
    }

    public ResponseEntity<?> finishPost(Long postId, User user) {
        Post findPost = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("존재하지 않는 글 입니다.")
        );
        if(!findPost.getUser().getId().equals(user.getId())){
            throw new UserNotFoundException("권한이 존재하지 않습니다");
        }

        findPost.setStatus("false");
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }




    // Post 작성 json 데이터 예외처리
    private void PostWriteException(PostRequestDto postRequestDto)
    {
        // 전달받은 json 데이터에서 전달받은 제목값이 null이라면 예외처리
        if (postRequestDto.getTitle().equals(""))
        {
            throw new PostWriteTitleNullException("제목이 입력되지 않았습니다.");
        }
        // 전달받은 json 데이터에서 전달받은 내용값이 null이라면 예외처리
        if (postRequestDto.getContent().equals(""))
        {
            throw new PostWriteContentNullException("내용이 입력되지 않았습니다.");
        }
        // 전달받은 json 데이터에서 전달받은 범주값이 null이라면 예외처리
        if (postRequestDto.getCategory().equals(""))
        {
            throw new PostWriteCategoryNullException("범주가 설정되지 않았습니다.");
        }
        // 전달받은 json 데이터에서 전달받은 난이도값이 null이라면 예외처리
        if (postRequestDto.getLevel().equals(""))
        {
            throw new PostWriteLevelNullException("난이도가 설정되지 않았습니다.");
        }

//        if (postRequestDto.getContent().length() > 1000)
//        {
//            throw new IllegalArgumentException("1000자 이하로 입력해주세요.");
//        }

    }

    // 유저 로그인 검사
    private void UserCheck(UserDetailsImpl userDetails)
    {
        if ( userDetails == null)
        {
            throw new LoginUserNotFoundException("로그인 상태가 아닙니다.");
        }
    }

    // @Transactional을 사용하기 위해 userRepository에서 불러온 유저를 반환
    private User GetUser(UserDetailsImpl userDetails)
    {
        return userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new LoginUserNotFoundException("업적 달성 유저를 찾을 수 없습니다."));
    }



    // Post 작성 및 저장
    private void PostWrite(PostRequestDto postRequestDto, User user)
    {
        // 파라미터 값을 통해 post 기본 칼럼 ( 제목, 내용, 범주, 난이도 ) 적용 후 생성 및 저장

        Post post = postRepository.save(new Post(postRequestDto, user));

        // 파라미터의 이미지 파일 url의 갯수만큼 반복
        for (String url : postRequestDto.getFile())
        {
            // post의 파일 url 리스트를 가지고 있을 postFile 엔티티 생성 및 저장
            PostFile saveFile = postFileRepository.save(new PostFile(url, post));
            // 저장된 postFile을 저장된 post의 fileList에 한개씩 추가함
            post.getFileList().add(saveFile);
        }

        // 잔여시간 처리
        if (postRequestDto.getTimeSet() == 0)
        {
            post.setDeadLine(null);
        }
        else
        {
            LocalDateTime deadline = post.getCreatedAt().plusHours(postRequestDto.getTimeSet());
            post.setDeadLine(deadline);
        }
    }


    private void PostWriteAddPoint(User user)
    {
        Long postCount = postRepository.countByUser(user);
        if (postCount == 3 || postCount == 6)
        {
            user.addPoint(50);
        }
    }



}


