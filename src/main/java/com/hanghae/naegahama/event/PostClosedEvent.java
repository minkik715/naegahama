package com.hanghae.naegahama.event;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class PostClosedEvent {
    User receiver;

    Post post;



    public PostClosedEvent(User receiver,  Post post) {
        this.receiver = receiver;
        this.post = post;
    }
}
