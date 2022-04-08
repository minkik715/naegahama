package com.hanghae.naegahama.service;

import com.hanghae.naegahama.repository.answerlikerepository.AnswerLikeQuerydslRepository;
import com.hanghae.naegahama.repository.answerlikerepository.AnswerLikeRepository;
import com.hanghae.naegahama.util.ComfortMethods;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.answerlike.AnswerLikeRequestDto;
import com.hanghae.naegahama.dto.answerlike.AnswerLikeResponseDto;
import com.hanghae.naegahama.event.AnswerLikeEvent;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerLikeService {
    private final AnswerLikeRepository answerLikeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final AnswerLikeQuerydslRepository answerLikeQuerydslRepository;


    @Transactional
    public AnswerLikeResponseDto AnswerLike(Long answerId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Answer answer = ComfortMethods.getAnswer(answerId);

        User answerWriter = answer.getUser();

        //오 좋은 거 배우고 갑니다.
        AnswerLike findAnswerLike = answerLikeRepository.findByUserAndAnswer(user,answer).orElse(null);

        if(findAnswerLike == null){
            AnswerLike answerLike = new AnswerLike(new AnswerLikeRequestDto(user, answer));
            answerLikeRepository.save(answerLike);
            applicationEventPublisher.publishEvent(new AnswerLikeEvent(answerWriter,user,answer));
        } else {
            answerLikeRepository.deleteById(findAnswerLike.getId());
            if(!answerWriter.getNickName().equals(user.getNickName())) {
                answerWriter.setPoint(answerWriter.getPoint() - 25);
            }
        }

        return new AnswerLikeResponseDto(answerId, answerLikeQuerydslRepository.countAnsLikes(answer.getId()));
    }
}
