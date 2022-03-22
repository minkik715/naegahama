package com.hanghae.naegahama.dto.event;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeEvent {
    User receiver;
    User sender;

    Post post;



    public PostLikeEvent(User receiver, User sender, Post post) {
        this.receiver = receiver;
        this.sender = sender;
        this.post = post;
    }
}