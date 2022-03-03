package com.hanghae.naegahama.dto.message;

import com.hanghae.naegahama.domain.MessageType;
import lombok.Getter;

@Getter
public class MessageRequestDto {

    private MessageType type;
    private Long roomId;
    private Long userId;
    private String sender;
    private String message;
    private String createdAt;

}

