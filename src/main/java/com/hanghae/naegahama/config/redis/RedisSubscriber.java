package com.hanghae.naegahama.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.naegahama.dto.message.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public void sendMessage(String publishMessage){
        try{
            MessageResponseDto messageResponseDto = objectMapper.readValue(publishMessage,MessageResponseDto.class);
            simpMessageSendingOperations.convertAndSend("/sub/api/chat/rooms" + messageResponseDto.getRoomId(), messageResponseDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
