package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AnswerService
{
    private final AnswerRepository answerRepository;
    private final S3Uploader s3Uploader;

    public ResponseEntity<?> answerWrite(AnswerPostRequestDto answerPostRequestDto, MultipartFile multipartFile, Long postId, UserDetailsImpl userDetails)
            throws IOException
    {
        String Url = s3Uploader.upload(multipartFile, "static");

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public String fileTest(MultipartFile multipartFile) throws IOException
    {
        return s3Uploader.upload(multipartFile, "test");
    }
}
