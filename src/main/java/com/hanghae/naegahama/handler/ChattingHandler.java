/*

package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.config.jwt.JwtAuthenticationProvider;
import com.hanghae.naegahama.handler.ex.RoomNotFoundException;
import com.hanghae.naegahama.repository.RedisRepository;
import com.hanghae.naegahama.service.ChatService;
import com.hanghae.naegahama.service.MessageService;
import com.hanghae.naegahama.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChattingHandler implements ChannelInterceptor {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final ChatService chatService;
    private final RedisRepository repository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == StompCommand.CONNECT){
            String jwtToken = accessor.getFirstNativeHeader("token");
            jwtAuthenticationProvider.validateToken(jwtToken);

        }
        else if(accessor.getCommand() == StompCommand.SUBSCRIBE){
            String simpleDestination = (String) message.getHeaders().get("simpleDestination");
            if(simpleDestination == null){
                throw new RoomNotFoundException("존재하지 않는 방입니다.");
            }
            String roomId = chatService.getRoomId(simpleDestination);

            String simpSessionId = (String)message.getHeaders().get("simpSessionId");

            repository.mappingUserRoom(roomId, simpSessionId);
        }
    }
}

*/
