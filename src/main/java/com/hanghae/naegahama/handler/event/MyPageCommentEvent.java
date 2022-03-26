package com.hanghae.naegahama.handler.event;



import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.domain.UserComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class MyPageCommentEvent {

    User receiver;
    User sender;
    UserComment userComment;



    public MyPageCommentEvent(User receiver, User sender, UserComment userComment) {

        this.receiver = receiver;
        this.sender = sender;
        this.userComment = userComment;
    }


}
