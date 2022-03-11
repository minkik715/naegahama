
package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.dto.answer.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AnswerController
{
    private final AnswerService answerService;

    // 답변글 작성
    @PostMapping("/answer/{postId}")
    public ResponseEntity<?> answerWrite (@RequestBody AnswerPostRequestDto answerPostRequestDto,
             @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerWrite(answerPostRequestDto,postId, userDetails.getUser());
//        answerService.answerWrite(answerPostRequestDto, multipartFile,postId, userDetails);
    }


    @ResponseBody
    @GetMapping("/answer/{postId}")
    public List<AnswerGetResponseDto> answerList(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerList(postId, userDetails);
    }


    @PutMapping("/answer/{answerId}")
    public ResponseEntity<?> answerUpdate (@PathVariable Long answerId,@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody AnswerPutRequestDto answerPutRequestDto)
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
    public ResponseEntity<?> answerDelete ( @PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return answerService.answerDelete(answerId, userDetails);
    }

    @GetMapping("/answer/detail/{answerId}")
    public AnswerDetailGetResponseDto answerDetail (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails )
    {
        return answerService.answerDetail(answerId,userDetails);
    }

    @PostMapping("/star/{answerId}")
    public ResponseEntity<?> answerStar (@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody StarPostRequestDto starPostRequestDto)
    {
        return answerService.answerStar(answerId,userDetails,starPostRequestDto);
    }

}

