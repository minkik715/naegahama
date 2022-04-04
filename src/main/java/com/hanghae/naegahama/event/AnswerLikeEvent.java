package com.hanghae.naegahama.event;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AnswerLikeEvent {
    User receiver;
    User sender;

    Answer answer;



    public AnswerLikeEvent(User receiver, User sender, Answer answer) {
        this.receiver = receiver;
        this.sender = sender;
        this.answer = answer;
    }
}
