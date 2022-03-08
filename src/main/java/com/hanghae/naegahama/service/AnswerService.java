package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerDetailGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import com.hanghae.naegahama.dto.answer.StarPostRequestDto;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




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

    private final S3Uploader s3Uploader;

    private final String publishing = "작성완료";
    private final String temporary = "임시저장";


    // 답변글 작성
    @Transactional
    public ResponseEntity<?> answerWrite(AnswerPostRequestDto answerPostRequestDto, Long postId, User user)
    {
        //answer와 연결된 post를 찾고
        Post post = postRepository.findPostById(postId);

        //filelist가 빈 Answer를 미리 하나 만들어두고
        Answer answer = new Answer(answerPostRequestDto,post,user, publishing);

        //저장된 Answer을 꺼내와서
        Answer saveAnwser = answerRepository.save(answer);

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
        AnswerVideo saveVideo = answerVideoRepository.save(videoUrl);

        // 임시 작성중이던 모든 글 삭제
        List<Answer> deleteList = answerRepository.findAllByUserAndState(user, temporary);

        for ( Answer deleteAnswer : deleteList)
        {
            answerFileRepository.deleteByAnswer(deleteAnswer);
            answerRepository.deleteById(deleteAnswer.getId());
        }

        // 최초 요청글 작성시 업적 5 획득
        User achievementUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        achievementUser.getAchievement().setAchievement9(1);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    // 답변글 임시 작성
    public ResponseEntity<?> temporaryAnswer(AnswerPostRequestDto answerPostRequestDto, Long postId, User user)
    {
        //answer와 연결된 post를 찾고
        Post post = postRepository.findPostById(postId);

        //filelist가 빈 Answer를 미리 하나 만들어두고
        Answer answer = new Answer(answerPostRequestDto,post,user, temporary);

        //저장된 Answer을 꺼내와서
        Answer saveAnwser = answerRepository.save(answer);

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
        AnswerVideo saveVideo = answerVideoRepository.save(videoUrl);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public List<String> fileTest(List<MultipartFile> multipartFile) throws IOException
    {
        List<String> urlList = new ArrayList<>();

        for ( MultipartFile file : multipartFile)
        {

            String url = s3Uploader.upload(file, "static");
            urlList.add(url);
            log.info(url);
        }

         return urlList;
    }

    // 요청 글에 달린 answerList
    public List<AnswerGetResponseDto> answerList(Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<Answer> answerList = answerRepository.findAllByPostIdOrderByCreatedAt(postId);
        List<AnswerGetResponseDto> answerGetResponseDtoList = new ArrayList<>();

        for ( Answer answer : answerList)
        {
            Long commentCount = commentRepository.countByAnswer(answer);
            Long likeCount = answerLikeRepository.countByAnswer(answer);
            int imageCount = answer.getFileList().size();
            AnswerGetResponseDto answerGetResponseDto = new AnswerGetResponseDto(answer,commentCount,likeCount,imageCount);
            answerGetResponseDtoList.add(answerGetResponseDto);
        }

        return answerGetResponseDtoList;

    }



    public ResponseEntity<?> answerUpdate(Long answerId, UserDetailsImpl userDetails, AnswerPostRequestDto answerPostRequestDto, List<MultipartFile> multipartFile) throws IOException
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        List<AnswerFile> fileList = new ArrayList<>();

        for ( MultipartFile file : multipartFile)
        {
            String url = s3Uploader.upload(file, "static");
            AnswerFile fileUrl = new AnswerFile(url);
            fileList.add(fileUrl);
        }

        answer.Update(answerPostRequestDto,fileList);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> answerDelete(Long answerId, UserDetailsImpl userDetails)
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        answerLikeRepository.deleteByAnswer(answer);
        answerFileRepository.deleteByAnswer(answer);
        commentRepository.deleteByAnswer(answer);

        answerRepository.deleteById(answerId);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    // 답변글 상세 조회
    public AnswerDetailGetResponseDto answerDetail(Long answerId, UserDetailsImpl userDetails)
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

        return answerDetailGetResponseDto;
    }

    @Transactional
    public ResponseEntity<?> answerStar(Long answerId, UserDetailsImpl userDetails, StarPostRequestDto starPostRequestDto)
    {
        User requestWriter = userDetails.getUser();

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        if( answer.getStar() != 0 )
        {
            throw new IllegalArgumentException("이미 평가한 답글입니다.");
        }

        answer.Star(starPostRequestDto);
        User answerWriter = answer.getUser();

        // 1점을 받을 시 업적 1 획득
        if ( starPostRequestDto.getStar() == 1)
        {
            answerWriter.getAchievement().setAchievement1(1);
        }
        // 5점을 받을 시 업적 2 획득
        else if( starPostRequestDto.getStar() == 5)
        {
            answerWriter.getAchievement().setAchievement2(1);
        }

        // 최초 평가시 업적 7 획득
        User achievementUser = userRepository.findById(requestWriter.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        achievementUser.getAchievement().setAchievement7(1);



        answerWriter.addPoint(starPostRequestDto.getStar());


        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }



}
