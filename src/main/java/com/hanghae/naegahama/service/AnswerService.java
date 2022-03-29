package com.hanghae.naegahama.service;

import com.hanghae.naegahama.comfortmethod.ComfortMethods;
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
    private final AnswerVideoRepository answerVideoRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final S3Uploader s3Uploader;
    private final VideoEncode videoEncode;


    // 답변글 작성



    // 요청 글에 달린 answerList 조회
    @Transactional(readOnly = true)
    public List<AnswerGetResponseDto>answerList(Long postId) {

        List<Answer> answerList = answerRepository.findAllByPostIdOrderByCreatedAt(postId);
        List<AnswerGetResponseDto> answerGetResponseDtoList = new ArrayList<>();
        for (Answer answer : answerList) {
            AnswerGetResponseDto answerGetResponseDto = new AnswerGetResponseDto(
                    answer,
                    commentRepository.countByAnswer(answer),
                    answerLikeRepository.countByAnswer(answer),
                    answer.getFileList().size()
            );
            answerGetResponseDtoList.add(answerGetResponseDto);
        }

        return answerGetResponseDtoList;

    }


    // 응답글 수정
    public BasicResponseDto answerUpdate(Long answerId, UserDetailsImpl userDetails, AnswerPutRequestDto answerPutRequestDto )
    {
        Answer answer = ComfortMethods.getAnswer(answerId);
        Post post = postRepository.findPostById(answer.getPost().getId());
        if(post.getStatus().equals("closed")){
            return (new BasicResponseDto("마감이 된 글에는 답변을 수정할 수 없습니다."));
        }
        if (!answer.getUser().getId().equals(userDetails.getUser().getId())) {
            return (new BasicResponseDto("작성자만 수정할 수 있습니다."));
        }
        answer.Update(answerPutRequestDto);
        answerFileRepository.deleteByAnswer(answer);
        answerVideoRepository.deleteByAnswer(answer);
        createfileUrl(answer, answerPutRequestDto.getFile());
        AnswerVideo videoUrl = new AnswerVideo(answerPutRequestDto.getVideo(),answer);
        ;
        answerVideoRepository.save(videoUrl);
        return new BasicResponseDto("success");
    }

    public BasicResponseDto answerDelete(Long answerId, UserDetailsImpl userDetails)
    {
        Answer answer = ComfortMethods.getAnswer(answerId);
        if (!answer.getUser().getId().equals(userDetails.getUser().getId())) {
            return (new BasicResponseDto("작성자만 삭제할 수 있습니다."));
        }
        answerRepository.deleteById(answerId);
        return new BasicResponseDto("success");
    }


    // 답변글 상세 조회
    @Transactional(readOnly = true)
    public AnswerDetailGetResponseDto answerDetail(Long answerId)
    {
        Answer answer =  ComfortMethods.getAnswer(answerId);
        List<Long> likeUserList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();
        for (AnswerFile answerFile : answerFileRepository.findAllByAnswerOrderByCreatedAt(answer)) {
            fileList.add(answerFile.getUrl());
        }
        for ( AnswerLike likeUser : answer.getLikeList())
        {
            likeUserList.add(likeUser.getUser().getId());
        }
        AnswerDetailGetResponseDto answerDetailGetResponseDto = new AnswerDetailGetResponseDto(
                answer,
                answerLikeRepository.countByAnswer(answer),
                commentRepository.countByAnswer(answer),
                likeUserList,
                fileList,
                answer.getPost().getCategory()
        );

        return answerDetailGetResponseDto;
    }

    public BasicResponseDto answerStar(Long answerId, UserDetailsImpl userDetails, StarPostRequestDto starPostRequestDto)
    {
        Answer answer = ComfortMethods.getAnswer(answerId);
        if( answer.getStar() != 0 )
        {
            throw new IllegalArgumentException("이미 평가한 답글입니다.");
        }
        answer.Star(starPostRequestDto);
        applicationEventPublisher.publishEvent(new StarGiveEvent(answer.getUser(),userDetails.getUser(),answer,starPostRequestDto.getStar()));
        return new BasicResponseDto("success");
    }

    public BasicResponseDto answerVideo(Long answerId, UserDetailsImpl userDetails) throws IOException
    {
        Answer answer = ComfortMethods.getAnswer(answerId);
        AnswerVideo video = answerVideoRepository.findByAnswer(answer).orElseThrow(
                () -> new IllegalArgumentException("해당 영상은 존재하지 않습니다."));
        String fileName = URLDecoder.decode(video.getUrl().split("static/")[1], "UTF-8");
        InputStream in = s3Uploader.getObject("static/" + fileName).getObjectContent();
        File tempFile = File.createTempFile(String.valueOf(in.hashCode()),".mp4");
        tempFile.deleteOnExit();
        FileUtils.copyInputStreamToFile(in,tempFile);
        String uuid = UUID.randomUUID().toString();
        videoEncode.videoEncode(tempFile.getAbsolutePath(),System.getProperty("user.dir") + "/test" + fileName);
        videoEncode.cutVideo(tempFile.getAbsolutePath(), System.getProperty("user.dir") + "/shorts" + fileName);
        File file1 = new File(System.getProperty("user.dir") + "/test" + fileName);
        File file2 = new File(System.getProperty("user.dir") + "/shorts" + fileName);
        if (videoEncode.getVideoLength(tempFile.getAbsolutePath()) < 15)
        {
            video.setUrl(s3Uploader.upload(file2, "static", true, uuid));
        }
        s3Uploader.upload(file2, "static", true, uuid);
        video.setUrl(s3Uploader.upload(file1, "static", false, uuid));
       s3Uploader.deleteS3("static/" + fileName);
        return new BasicResponseDto("success");

    }

    public Long answerWrite(List<MultipartFile> multipartFileList, MultipartFile videoFile, AnswerPostRequestDto answerPostRequestDto, Long postId, User user)  throws IOException
    {
        List<String> file = new ArrayList<>();
        String video = "";
        if ( multipartFileList != null)
        {
            for ( MultipartFile multipartFile : multipartFileList)
            {
                String fileUrl = s3Uploader.upload(multipartFile, "static",false);
                file.add(fileUrl);
            }
        }
        if ( videoFile != null)
        {
            video = s3Uploader.upload(videoFile, "static",true);
        }
        FileResponseDto fileResponseDto = new FileResponseDto(file, video);
        Post post = postRepository.findPostById(postId);
        if(post.getStatus().equals("false"))
        {
            return 0L;
        }
        Answer saveAnwser = answerRepository.save(new Answer(answerPostRequestDto,post,user));
        createfileUrl(saveAnwser, fileResponseDto.getFile());
        AnswerVideo videoUrl = new AnswerVideo(fileResponseDto.getVideo(),saveAnwser);
        answerVideoRepository.save(videoUrl);
        applicationEventPublisher.publishEvent(new AnswerWriteEvent(post.getUser(), saveAnwser.getUser(),post, saveAnwser));
        return saveAnwser.getId();
    }

    private void createfileUrl(Answer saveAnwser, List<String> file2) {
        for (String url : file2) {
            AnswerFile fileUrl = new AnswerFile(url, saveAnwser);
            answerFileRepository.save(fileUrl);
        }
    }


}
