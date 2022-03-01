/*
package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.message.MessageRequestDto;
import com.hanghae.naegahama.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;

    @MessageMapping("/api/chat/message")
    public void message(@RequestBody MessageRequestDto messageRequestDto, @Header("token") String token){
        chatService.messageResolver(messageRequestDto,token);
    }


}
*/
