package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.MyPage.*;
import com.hanghae.naegahama.dto.userpagecommentdto.UserCommentRequestDto;
import com.hanghae.naegahama.dto.userpagecommentdto.UserCommentResponseDto;
import com.hanghae.naegahama.dto.userpagecommentdto.UserPageCommentListResponseDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.UserPageService;
import com.hanghae.naegahama.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserPageController {
    private final UserPageService userPageService;
    private final UserService userService;

    @GetMapping("/userpage/post/{userid}")
    public List<MyPostDto> userPost(@PathVariable Long userid)
    {
        return userService.userPost(userid);
    }

    @GetMapping("/userpage/answer/{userid}")
    public List<MyAnswerDto> userAnswer(@PathVariable Long userid)
    {
        return userService.userAnswer(userid);
    }

    @GetMapping("/userpage/banner/{userid}")
    public MyBannerDto userBanner(@PathVariable Long userid)
    {
        return userService.userBanner(userid);
    }

    @GetMapping("/userpage/achievement/{userid}")
    public MyAchievementDto userAchievement(@PathVariable Long userid)
    {
        return userService.userAchievement(userid);
    }

    @GetMapping("/userpage/count/{userid}")
    public MyCountDto usercount(@PathVariable Long userid)
    {
        return userService.usercount(userid);
    }

    @PostMapping("/userpage/comment/{userId}")
    public ResponseEntity<UserCommentResponseDto> writeUserPageComment(@RequestBody UserCommentRequestDto userCommentRequestDto, @PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userPageService.writeUserPageComment(userId, userDetails, userCommentRequestDto);
    }

    @GetMapping("/userpage/comment/{userId}")
    public ResponseEntity<UserPageCommentListResponseDto> getUserPageCommentList(@PathVariable Long userId){
        return userPageService.getUserPageCommentList(userId);
    }

    @PutMapping("/userpage/comment/{commentId}")
    public ResponseEntity<?> modifyUserPageCommentList(@RequestBody UserCommentRequestDto userCommentRequestDto, @PathVariable Long commentId){
        return userPageService.modifyUserPageCommentList(userCommentRequestDto, commentId);
    }

    @DeleteMapping("/userpage/comment/{commentId}")
    public ResponseEntity<?> deleteUserPageCommentList(@PathVariable Long commentId){
        return userPageService.deleteUserPageCommentList(commentId);
    }

}
