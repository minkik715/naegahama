package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import com.hanghae.naegahama.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/answer")
@RestController
@RequiredArgsConstructor
public class AnswerController
{
    private final AnswerService answerService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> answerWrite
            (@RequestPart(value = "content") AnswerPostRequestDto answerPostRequestDto,
             @RequestPart(value = "imageUrl", required = false) MultipartFile multipartFile,
             @PathVariable Long postId,
             @AuthenticationPrincipal UserDetailsImpl userDetails)throws IOException

    {
        return answerService.answerWrite(answerPostRequestDto, multipartFile,postId, userDetails);
    }


    @PostMapping("/file")
    public String fileUpload(@RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException
    {
        return answerService.fileTest(multipartFile);
    }
}
