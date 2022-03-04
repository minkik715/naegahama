package com.hanghae.naegahama.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.naegahama.dto.message.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public RedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(String publishMessage){
        try{
            MessageResponseDto messageResponseDto = objectMapper.readValue(publishMessage,MessageResponseDto.class);
            messagingTemplate.convertAndSend("/sub/api/chat/rooms" + messageResponseDto.getRoomId(), messageResponseDto);
        }catch (Exception e){
            log.error("Exception {}", e);
        }
    }

}
