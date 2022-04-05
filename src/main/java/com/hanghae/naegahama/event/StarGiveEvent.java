package com.hanghae.naegahama.event;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class StarGiveEvent {
    User receiver;
    User sender;

    Answer answer;

    Integer star;

    public StarGiveEvent(User receiver, User sender, Answer answer,Integer star) {
        this.receiver = receiver;
        this.sender = sender;
        this.answer = answer;
        this.star =star;
    }
}
