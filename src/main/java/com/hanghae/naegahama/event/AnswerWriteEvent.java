package com.hanghae.naegahama.event;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AnswerWriteEvent {
    User receiver;
    User sender;
    Answer answer;
    Post post;



    public AnswerWriteEvent(User receiver, User sender, Post post, Answer answer) {
        this.receiver = receiver;
        this.sender = sender;
        this.post = post;
        this.answer = answer;
    }
}
