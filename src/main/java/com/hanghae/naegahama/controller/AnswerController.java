
package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.answer.AnswerDetailGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import com.hanghae.naegahama.dto.answer.StarPostRequestDto;
import com.hanghae.naegahama.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AnswerController
{
    private final AnswerService answerService;

    @PostMapping("/answer/{postId}")
    public ResponseEntity<?> answerWrite
            (@RequestPart (value = "post") AnswerPostRequestDto answerPostRequestDto,
             @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile,
             @PathVariable Long postId,
             @AuthenticationPrincipal UserDetailsImpl userDetails)throws IOException

    {
        return answerService.answerWrite(answerPostRequestDto, multipartFile,postId, userDetails);
//        answerService.answerWrite(answerPostRequestDto, multipartFile,postId, userDetails);
    }

    @ResponseBody
    @GetMapping("/answer/{postId}")
    public List<AnswerGetResponseDto> answerList(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerList(postId, userDetails);
    }



    @PostMapping("/answer/file")
    public List<String> fileUpload(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) throws IOException
    {
        return answerService.fileTest(multipartFile);
    }


//    @PutMapping("/{answerId}")
//    public ResponseEntity<?> answerUpdate (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails)
//    {
//
//    }
    @PatchMapping("/answer/{answerId}")
    public ResponseEntity<?> answerUpdate (
            @PathVariable Long answerId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart (value = "post") AnswerPostRequestDto answerPostRequestDto,
            @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) throws IOException
    {
        return answerService.answerUpdate(answerId, userDetails, answerPostRequestDto, multipartFile);
    }

    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<?> answerDelete ( @PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerDelete(answerId, userDetails);
    }

    @GetMapping("/answer/detail/{answerId}")
    public AnswerDetailGetResponseDto answerDetail (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails )
    {
        return answerService.answerDetail(answerId,userDetails);
    }

    @PostMapping("/answer/star/{answerId}")
    public ResponseEntity<?> answerStar (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody StarPostRequestDto starPostRequestDto)
    {
        return answerService.answerStar(answerId,userDetails,starPostRequestDto);
    }

}

