package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.domain.UserComment;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.userpagecommentdto.UserCommentRequestDto;
import com.hanghae.naegahama.dto.userpagecommentdto.UserCommentResponseDto;
import com.hanghae.naegahama.dto.userpagecommentdto.UserPageCommentListResponseDto;
import com.hanghae.naegahama.handler.event.MyPageCommentEvent;
import com.hanghae.naegahama.ex.CommentNotFoundException;
import com.hanghae.naegahama.ex.UserNotFoundException;
import com.hanghae.naegahama.repository.UserPageCommentRepository;
import com.hanghae.naegahama.repository.UserRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserPageService {

    private final UserRepository userRepository;
    private final UserPageCommentRepository userPageCommentRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UserCommentResponseDto writeUserPageComment(Long userId, UserDetailsImpl userDetails, UserCommentRequestDto commentRequestDto) {

        User pageUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("유저를 찾을 수 없습니다.")
        );
        User writer = userDetails.getUser();

        //댓글 작성
        UserComment userComment = null;
        if(commentRequestDto.getParentId() == null) {
            userComment = userPageCommentRepository.save(new UserComment(commentRequestDto, pageUser, writer));

        }else{
            userComment = userPageCommentRepository.save(new UserComment(commentRequestDto, pageUser, writer));
        }

        applicationEventPublisher.publishEvent(new MyPageCommentEvent(pageUser,writer,userComment));

        return new UserCommentResponseDto(userComment);


    }

    public UserPageCommentListResponseDto getUserPageCommentList(Long userId) {
        User pageUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("유저를 찾을 수 없습니다.")
        );

        List<UserComment> byPageUserOrderByModifiedAt = userPageCommentRepository.findByPageUserOrderByModifiedAt(pageUser);

        List<UserCommentResponseDto> allUserPageCommentResponseDtos = new ArrayList<>();
        for (UserComment userComment : byPageUserOrderByModifiedAt) {

            if(userComment.getParentCommentId() == null){
                UserCommentResponseDto userCommentResponseDto = new UserCommentResponseDto(userComment);

                List<UserComment> byParentCommentIdOrderByModifiedAt = userPageCommentRepository.findByParentCommentIdOrderByModifiedAt(userComment.getId());
                ArrayList<UserCommentResponseDto> childUserCommentResponseDtos = new ArrayList<>();
                for (UserComment comment : byParentCommentIdOrderByModifiedAt) {
                    childUserCommentResponseDtos.add(new UserCommentResponseDto(comment));
                }
                userCommentResponseDto.setChildComments(childUserCommentResponseDtos);
                allUserPageCommentResponseDtos.add(userCommentResponseDto);
            }

        }


        return new UserPageCommentListResponseDto(userId, allUserPageCommentResponseDtos);



    }

    public UserCommentResponseDto modifyUserPageCommentList(UserCommentRequestDto userCommentRequestDto,Long commentId) {
        UserComment findComment = userPageCommentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("댓글을 찾을 수 없습니다.")
        );
        findComment.setComment(userCommentRequestDto.getContent());

        return new UserCommentResponseDto(findComment);

    }

    public BasicResponseDto deleteUserPageCommentList(Long commentId) {
        userPageCommentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        userPageCommentRepository.deleteAll(userPageCommentRepository.findByParentCommentIdOrderByModifiedAt(commentId));
        userPageCommentRepository.deleteById(commentId);
        return new BasicResponseDto("success");
    }
}
