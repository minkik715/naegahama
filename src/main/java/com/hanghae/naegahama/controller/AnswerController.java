package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.*;
import com.hanghae.naegahama.dto.file.FileSizeCheckDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AnswerController
{
    private final AnswerService answerService;

    // 답변글 작성
    @PostMapping("/answer/{postId}")
    public ResponseEntity<?> answerWrite (@RequestBody @Validated AnswerPostRequestDto answerPostRequestDto,
             @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerWrite(answerPostRequestDto,postId, userDetails.getUser());
    }


    @ResponseBody
    @GetMapping("/answer/{postId}")
    public ResponseEntity<List<AnswerGetResponseDto>> answerList(@PathVariable Long postId)
    {

        return answerService.answerList(postId);
    }


    @PutMapping("/answer/{answerId}")
    public ResponseEntity<BasicResponseDto> answerUpdate (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @RequestBody @Validated AnswerPutRequestDto answerPutRequestDto)
    {
        return answerService.answerUpdate(answerId, userDetails, answerPutRequestDto);
    }

//    // 임시 저장 글 리스트 불러오기
//    @GetMapping("/answer/temporary")
//    public ResponseEntity<?> temporaryLoad(@AuthenticationPrincipal UserDetailsImpl userDetails)
//    {
//        return answerService.temporaryLoad(userDetails);
//    }

    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<BasicResponseDto> answerDelete ( @PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerDelete(answerId, userDetails);
    }

    @GetMapping("/answer/detail/{answerId}")
    public ResponseEntity<AnswerDetailGetResponseDto> answerDetail (@PathVariable Long answerId )
    {
        return answerService.answerDetail(answerId);
    }

    @PostMapping("/star/{answerId}")
    public ResponseEntity<BasicResponseDto> answerStar (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody @Validated StarPostRequestDto starPostRequestDto)
    {
        return answerService.answerStar(answerId,userDetails,starPostRequestDto);
    }

    @PostMapping("/video/{answerId}")
    public ResponseEntity<BasicResponseDto> answerVideo (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails ) throws IOException
    {
        return answerService.answerVideo(answerId,userDetails);
    }


    @PostMapping("/answer2/{postId}")
    public ResponseEntity<?> answerWrite2 (@RequestPart(name = "file", required = false) List<MultipartFile> multipartFileList,
                                           @RequestPart(name = "video",required = false) MultipartFile videoFile,
                                           @RequestPart(name = "answer") @Validated AnswerPostRequestDto2 answerPostRequestDto,
                                           @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)  throws IOException
    {
        return answerService.answerWrite2(multipartFileList, videoFile, answerPostRequestDto,postId, userDetails.getUser());
    }

 



}

