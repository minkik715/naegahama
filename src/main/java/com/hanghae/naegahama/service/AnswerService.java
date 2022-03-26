package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.*;

import com.hanghae.naegahama.dto.file.FileResponseDto;
import com.hanghae.naegahama.handler.event.StarGiveEvent;
import com.hanghae.naegahama.handler.event.AnswerWriteEvent;

import com.hanghae.naegahama.ex.UserNotFoundException;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.util.S3Uploader;
import com.hanghae.naegahama.util.VideoEncode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AnswerService
{
    private final AnswerRepository answerRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final AnswerFileRepository answerFileRepository;
    private final UserRepository userRepository;
    private final AnswerVideoRepository answerVideoRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final S3Uploader s3Uploader;
    private final VideoEncode videoEncode;


    // 답변글 작성

    public ResponseEntity<?> answerWrite(AnswerPostRequestDto answerPostRequestDto, Long postId, User user)
    {
        Post post = postRepository.findPostById(postId);

        if(post.getStatus().equals("false"))
        {
            return ResponseEntity.badRequest().body("마감이 된 글에는 답변을 작성할 수 없습니다.");
        }

        //저장된 Answer을 꺼내와서
        Answer saveAnwser = answerRepository.save(new Answer(answerPostRequestDto,post,user));

        for ( String url : answerPostRequestDto.getFile())
        {
            // 이미지 파일 url로 answerFile 객체 생성
            AnswerFile fileUrl = new AnswerFile(url);

            // answerFile saveanswer를 연관관계 설정
            fileUrl.setAnswer(saveAnwser);

            // 이미지 파일 url 1개에 해당되는 answerFile을 DB에 저장
            AnswerFile saveFile = answerFileRepository.save(fileUrl);

            // 저장된 answerFile을 저장된 answer에 한개씩 추가함
            saveAnwser.getFileList().add(saveFile);
        }

        AnswerVideo videoUrl = new AnswerVideo(answerPostRequestDto.getVideo());
        videoUrl.setAnswer(saveAnwser);

        //빠뜨리신 재균님?
        answerVideoRepository.save(videoUrl);
        // 최초 요청글 작성시 업적 5 획득
        User answerUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));


        applicationEventPublisher.publishEvent(new AnswerWriteEvent(post.getUser(), answerUser,post,saveAnwser));


        return ResponseEntity.ok().body(saveAnwser.getId());
    }


    // 요청 글에 달린 answerList 조회
    public ResponseEntity<List<AnswerGetResponseDto>>answerList(Long postId) {

        List<Answer> answerList = answerRepository.findAllByPostIdOrderByCreatedAt(postId);
        List<AnswerGetResponseDto> answerGetResponseDtoList = new ArrayList<>();

        for (Answer answer : answerList) {
            Long commentCount = commentRepository.countByAnswer(answer);
            Long likeCount = answerLikeRepository.countByAnswer(answer);
            int imageCount = answer.getFileList().size();
            AnswerGetResponseDto answerGetResponseDto = new AnswerGetResponseDto(answer, commentCount, likeCount, imageCount);
            answerGetResponseDtoList.add(answerGetResponseDto);
        }

        return ResponseEntity.ok().body(answerGetResponseDtoList);

    }


    // 응답글 수정
    public ResponseEntity<BasicResponseDto> answerUpdate(Long answerId, UserDetailsImpl userDetails, AnswerPutRequestDto answerPutRequestDto )
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));
        Post post = postRepository.findPostById(answer.getPost().getId());
        if(post.getStatus().equals("false")){
            return ResponseEntity.badRequest().body(new BasicResponseDto("마감이 된 글에는 답변을 수정할 수 없습니다."));
        }

        answer.Update(answerPutRequestDto);

        // 기존에 있던 포스트파일 제거
        answerFileRepository.deleteByAnswer(answer);
        answerVideoRepository.deleteByAnswer(answer);

        // 새로운 이미지 파일 url 배열로 for 반복문을 실행
        for (String url : answerPutRequestDto.getFile()) {
            // 이미지 파일 url로 postFile 객체 생성
            AnswerFile fileUrl = new AnswerFile(url);

            // postFile에 savePost를 연관관계 설정
            fileUrl.setAnswer(answer);

            // 이미지 파일 url 1개에 해당되는 postFile을 DB에 저장
            AnswerFile saveFile = answerFileRepository.save(fileUrl);

            // 저장된 postFile을 저장된 post에 한개씩 추가함
            answer.getFileList().add(saveFile);
        }

        AnswerVideo videoUrl = new AnswerVideo(answerPutRequestDto.getVideo());
        videoUrl.setAnswer(answer);
        answerVideoRepository.save(videoUrl);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<BasicResponseDto> answerDelete(Long answerId, UserDetailsImpl userDetails)
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

     /*   answerLikeRepository.deleteByAnswer(answer);
        answerFileRepository.deleteByAnswer(answer);
        answerVideoRepository.deleteByAnswer(answer);
        commentRepository.deleteByAnswer(answer);*/
        answerRepository.deleteById(answerId);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    // 답변글 상세 조회
    public ResponseEntity<AnswerDetailGetResponseDto> answerDetail(Long answerId)
    {
        Answer answer =  answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        Long likeCount = answerLikeRepository.countByAnswer(answer);
        Long commentCount = commentRepository.countByAnswer(answer);

        List<AnswerLike> likeList = answer.getLikeList();
        List<Long> likeUserList = new ArrayList<>();

        List<AnswerFile> findAnswerFileList = answerFileRepository.findAllByAnswerOrderByCreatedAt(answer);

        List<String> fileList = new ArrayList<>();
        for (AnswerFile answerFile : findAnswerFileList) {
            fileList.add(answerFile.getUrl());
        }
        for ( AnswerLike likeUser : likeList)
        {
            likeUserList.add(likeUser.getUser().getId());
        }

//        AnswerVideo answerVideo = answerVideoRepository.findByAnswer(answer).orElseThrow(
//                () -> new IllegalArgumentException("비디오가 존재하지 않습니다."));


        AnswerDetailGetResponseDto answerDetailGetResponseDto = new AnswerDetailGetResponseDto(answer,likeCount,commentCount,likeUserList,fileList, answer.getPost().getCategory());

        return ResponseEntity.ok().body(answerDetailGetResponseDto);
    }

    @Transactional
    public ResponseEntity<BasicResponseDto> answerStar(Long answerId, UserDetailsImpl userDetails, StarPostRequestDto starPostRequestDto)
    {
        User requestWriter = userDetails.getUser();

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        if( answer.getStar() != 0 )
        {
            throw new IllegalArgumentException("이미 평가한 답글입니다.");
        }
        requestWriter = userRepository.findById(requestWriter.getId()).orElseThrow(
                () -> new UserNotFoundException("해당 유저는 존재하지 않습니다.")
        );

        answer.Star(starPostRequestDto);
        User answerWriter = answer.getUser();

        applicationEventPublisher.publishEvent(new StarGiveEvent(answerWriter,requestWriter,answer,starPostRequestDto.getStar()));



        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<BasicResponseDto> answerVideo(Long answerId, UserDetailsImpl userDetails) throws IOException
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        AnswerVideo video = answerVideoRepository.findByAnswer(answer).orElseThrow(
                () -> new IllegalArgumentException("해당 영상은 존재하지 않습니다."));

        String fileName = URLDecoder.decode(video.getUrl().split("static/")[1], "UTF-8");

        System.out.println(fileName);

        InputStream in = s3Uploader.getObject("static/" + fileName).getObjectContent();

        File tempFile = File.createTempFile(String.valueOf(in.hashCode()),".mp4");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(in,tempFile);

        String uuid = UUID.randomUUID().toString();

        videoEncode.videoEncode(tempFile.getAbsolutePath(),System.getProperty("user.dir") + "/test" + fileName);
        videoEncode.cutVideo(tempFile.getAbsolutePath(), System.getProperty("user.dir") + "/shorts" + fileName);

        File file1 = new File(System.getProperty("user.dir") + "/test" + fileName);
        File file2 = new File(System.getProperty("user.dir") + "/shorts" + fileName);


//        s3Uploader.upload(tempFile, "static", false, uuid);


        //        s3Uploader.removeNewFile(tempFile);
        if (videoEncode.getVideoLength(tempFile.getAbsolutePath()) < 15)
        {
            video.setUrl(s3Uploader.upload(file2, "static", true, uuid));
//             s3Uploader.upload(tempFile, "static", true, uuid);
        }

        s3Uploader.upload(file2, "static", true, uuid);
        video.setUrl(s3Uploader.upload(file1, "static", false, uuid));


       s3Uploader.deleteS3("static/" + fileName);



        return ResponseEntity.ok().body(new BasicResponseDto("true"));

    }

    public ResponseEntity<?> answerWrite2(List<MultipartFile> multipartFileList, MultipartFile videoFile, AnswerPostRequestDto2 answerPostRequestDto, Long postId, User user)  throws IOException
    {
        log.info("answer Write시작");
        // 파일 혹은 비디오 파일을 넣지 않았을 경우 공백으로 처리.
        List<String> file = new ArrayList<>();
        String video = "";

        log.info("answer 인코딩 시작");

        // 파일이 있을 경우 s3에 넣고 url 값을 받음.
        if ( multipartFileList != null)
        {
            for ( MultipartFile multipartFile : multipartFileList)
            {
                String fileUrl = s3Uploader.upload(multipartFile, "static",false);
                file.add(fileUrl);
            }
        }

        // 비디오가 있을 경우 s3에 넣고 url 값을 받음.
        if ( videoFile != null)
        {
            video = s3Uploader.upload(videoFile, "static",true);
        }

        FileResponseDto fileResponseDto = new FileResponseDto(file, video);





        Post post = postRepository.findPostById(postId);

        if(post.getStatus().equals("false"))
        {
            return ResponseEntity.badRequest().body("마감이 된 글에는 답변을 작성할 수 없습니다.");
        }
        log.info("answer 인코딩 중");

        //저장된 Answer을 꺼내와서
        Answer saveAnwser = answerRepository.save(new Answer(answerPostRequestDto,post,user));

        for ( String url : fileResponseDto.getFile())
        {
            // 이미지 파일 url로 answerFile 객체 생성
            AnswerFile fileUrl = new AnswerFile(url);

            // answerFile saveanswer를 연관관계 설정
            fileUrl.setAnswer(saveAnwser);

            // 이미지 파일 url 1개에 해당되는 answerFile을 DB에 저장
            AnswerFile saveFile = answerFileRepository.save(fileUrl);

            // 저장된 answerFile을 저장된 answer에 한개씩 추가함
            saveAnwser.getFileList().add(saveFile);
        }
        log.info("answer 비디오!");

        AnswerVideo videoUrl = new AnswerVideo(fileResponseDto.getVideo());
        videoUrl.setAnswer(saveAnwser);
        log.info("answer 비디오저장!");

        //빠뜨리신 재균님?
        answerVideoRepository.save(videoUrl);
        // 최초 요청글 작성시 업적 5 획득
        User answerUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));

        log.info("알람보내자");

        applicationEventPublisher.publishEvent(new AnswerWriteEvent(post.getUser(), answerUser,post, saveAnwser));



        return ResponseEntity.ok().body(saveAnwser.getId());
    }


}
