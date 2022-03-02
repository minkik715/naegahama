
package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.answer.AnswerGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import com.hanghae.naegahama.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/answer")
@RestController
@RequiredArgsConstructor
public class AnswerController
{
    private final AnswerService answerService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> answerWrite
            (@RequestPart (value = "post") AnswerPostRequestDto answerPostRequestDto,
             @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile,
             @PathVariable Long postId,
             @AuthenticationPrincipal UserDetailsImpl userDetails)throws IOException

    {
        return answerService.answerWrite(answerPostRequestDto, multipartFile,postId, userDetails);
//        answerService.answerWrite(answerPostRequestDto, multipartFile,postId, userDetails);
    }

    @GetMapping("/{postId}")
    public List<AnswerGetResponseDto> answerList(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerList(postId, userDetails);
    }



    @PostMapping("/file")
    public List<String> fileUpload(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) throws IOException
    {
        return answerService.fileTest(multipartFile);
    }


//    @PutMapping("/{answerId}")
//    public ResponseEntity<?> answerUpdate (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails)
//    {
//
//    }

}

