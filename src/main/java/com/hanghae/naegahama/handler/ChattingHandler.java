package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.config.jwt.JwtAuthenticationProvider;
import com.hanghae.naegahama.service.MessageService;
import com.hanghae.naegahama.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChattingHandler {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final RoomService chatRoomService;
    private MessageService chatMessageSerivce;
}
