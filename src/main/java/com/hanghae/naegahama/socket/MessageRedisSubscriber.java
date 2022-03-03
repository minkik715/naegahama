package com.hanghae.naegahama.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.naegahama.domain.Message;
import com.hanghae.naegahama.dto.message.MessageResponseDto;
import org.springframework.data.redis.core.RedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageRedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public MessageRedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }


    //convetAndSend 로 데이터를 보내면 여기서 잡아서 보낸다.
    // Redis 에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber 가 해당 메시지를 받아 처리한다.
    public void sendMessage(String publishMessage) {
        try {
            // ChatMessage 객채로 맵핑
            MessageResponseDto messageResponseDto = objectMapper.readValue(publishMessage, MessageResponseDto.class);
            // 채팅방을 구독한 클라이언트에게 메시지 발송
            messagingTemplate.convertAndSend("/sub/api/chat/rooms/" + messageResponseDto.getRoomId(), messageResponseDto);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
