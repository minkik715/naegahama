package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.*;
<<<<<<< HEAD

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/answer/{postId}")
    public List<AnswerGetResponseDto> answerList(@PathVariable Long postId)
    {
        return answerService.answerList(postId);
    }


    @GetMapping("/answer/detail/{answerId}")
    public AnswerDetailGetResponseDto answerDetail (@PathVariable Long answerId )
    {
        return answerService.answerDetail(answerId);
    }


    // 답변글 작성
    @PostMapping("/answer2/{postId}")
    public Long answerWrite (@RequestPart(name = "file", required = false) List<MultipartFile> multipartFileList,
                             @RequestPart(name = "video",required = false) MultipartFile videoFile,
                             @RequestPart(name = "answer") @Validated AnswerPostRequestDto answerPostRequestDto,
                             @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)  throws IOException
    {
        Long result = answerService.answerWrite(multipartFileList, videoFile, answerPostRequestDto, postId, userDetails.getUser());
        if(result.equals(0L)){
            return 0L;
        }else{
            return result;
        }
    }

    @PostMapping("/star/{answerId}")
    public BasicResponseDto answerStar (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody @Validated StarPostRequestDto starPostRequestDto)
    {
        return answerService.answerStar(answerId, userDetails, starPostRequestDto);
    }

    @PostMapping("/video/{answerId}")
    public BasicResponseDto answerVideo (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails ) throws IOException
    {
        return answerService.answerVideo(answerId, userDetails);
    }


    @PutMapping("/answer/{answerId}")
    public BasicResponseDto answerUpdate (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @RequestBody @Validated AnswerPutRequestDto answerPutRequestDto)
    {
        return answerService.answerUpdate(answerId, userDetails, answerPutRequestDto);
    }



    @DeleteMapping("/answer/{answerId}")
    public BasicResponseDto answerDelete ( @PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerDelete(answerId, userDetails);
    }



}

