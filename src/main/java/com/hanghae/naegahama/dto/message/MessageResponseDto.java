package com.hanghae.naegahama.dto.message;

import com.hanghae.naegahama.domain.Message;
import lombok.Getter;


@Getter
public class MessageResponseDto {
    private String roomId;
    private Long userId;
    private String sender;
    private String message;
    private String createdAt;

    public MessageResponseDto(Message message) {
        this.roomId = String.valueOf(message.getRoom().getId());
        this.userId = message.getUser().getId();
        this.sender = message.getUser().getNickName();
        this.message = message.getMessage();
        this.createdAt = message.getCreatedAt().toString();
    }
}
