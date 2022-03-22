package com.hanghae.naegahama.handler.event;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AnswerWriteEvent {
    User receiver;
    User sender;

    Post post;



    public AnswerWriteEvent(User receiver, User sender, Post post) {
        this.receiver = receiver;
        this.sender = sender;
        this.post = post;
    }
}
