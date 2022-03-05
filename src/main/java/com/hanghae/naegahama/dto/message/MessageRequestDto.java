package com.hanghae.naegahama.dto.message;

import com.hanghae.naegahama.domain.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class MessageRequestDto {
    public MessageRequestDto( Long roomId, MessageType messageType, String message ) {
        this.roomId = roomId;
        this.type = messageType;
        this.message =message;
    }

    public MessageRequestDto(MessageType type, Long roomId, Long userId, String sender, String message) {
        this.type = type;
        this.roomId = roomId;
        this.userId = userId;
        this.sender = sender;
        this.message = message;
    }

    private MessageType type;
    private Long roomId;
    private Long userId;
    private String sender;
    private String message;
    private String createdAt;

}

