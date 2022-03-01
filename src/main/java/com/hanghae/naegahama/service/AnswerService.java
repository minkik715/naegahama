package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.File;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
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
    private final LikeRepository likeRepository;

    private final S3Uploader s3Uploader;

    public ResponseEntity<?> answerWrite(AnswerPostRequestDto answerPostRequestDto, List<MultipartFile> multipartFile, Long postId, UserDetailsImpl userDetails)
            throws IOException
    {
//        String Url = s3Uploader.upload(multipartFile, "static");
        List<File> fileList = new ArrayList<>();

        User user = userDetails.getUser();

        for ( MultipartFile file : multipartFile)
        {
            String url = s3Uploader.upload(file, "static");
            File fileUrl = new File(url);
            fileList.add(fileUrl);
        }

        Post post = postRepository.findPostById(postId);

        Answer answer = new Answer(answerPostRequestDto,post,fileList,user);

        answerRepository.save(answer);

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

    public List<AnswerGetResponseDto> answerList(Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<Answer> answerList = answerRepository.findAllByPostId(postId);
        List<AnswerGetResponseDto> answerGetResponseDtoList = new ArrayList<>();

        for ( Answer answer : answerList)
        {
            Long commentCount = commentRepository.countByAnswer(answer);
            Long likeCount = likeRepository.countByAnswer(answer);

            AnswerGetResponseDto answerGetResponseDto = new AnswerGetResponseDto(answer,commentCount,likeCount);
            answerGetResponseDtoList.add(answerGetResponseDto);
        }

        return answerGetResponseDtoList;

    }
}
