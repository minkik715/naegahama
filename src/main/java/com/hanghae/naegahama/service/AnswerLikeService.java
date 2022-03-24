package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.answerlike.AnswerLikeRequestDto;
import com.hanghae.naegahama.dto.answerlike.AnswerLikeResponseDto;
import com.hanghae.naegahama.handler.event.AnswerLikeEvent;
import com.hanghae.naegahama.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerLikeService {
    private final AnswerLikeRepository answerLikeRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    public AnswerLikeResponseDto AnswerLike(Long answerId, Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("유저 정보가 없습니다.")
        );

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                ()->new IllegalArgumentException("답변글이 없습니다.")
        );

        User answerWriter = answer.getUser();

        //오 좋은 거 배우고 갑니다.
        AnswerLike findAnswerLike = answerLikeRepository.findByUserAndAnswer(user,answer).orElse(null);

        if(findAnswerLike == null){
            AnswerLikeRequestDto requestDto = new AnswerLikeRequestDto(user, answer);
            AnswerLike answerLike = new AnswerLike(requestDto);
            findAnswerLike = answerLikeRepository.save(answerLike);
            applicationEventPublisher.publishEvent(new AnswerLikeEvent(answerWriter,user,answer));
        } else {
            answerLikeRepository.deleteById(findAnswerLike.getId());
            if(!answerWriter.getNickName().equals(user.getNickName())) {
                answerWriter.setPoint(answerWriter.getPoint() - 25);
            }
        }

        return new AnswerLikeResponseDto(answerId, answerLikeRepository.countByAnswer(answer));
    }
}
