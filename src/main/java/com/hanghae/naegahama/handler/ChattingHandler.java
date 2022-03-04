

package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.config.jwt.JwtAuthenticationProvider;
import com.hanghae.naegahama.domain.MessageType;
import com.hanghae.naegahama.dto.message.MessageRequestDto;
import com.hanghae.naegahama.handler.ex.RoomNotFoundException;
import com.hanghae.naegahama.repository.RedisRepository;
import com.hanghae.naegahama.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChattingHandler implements ChannelInterceptor {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final ChatService chatService;
    private final RedisRepository repository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String jwtToken = accessor.getFirstNativeHeader("token");
        if (accessor.getCommand() == StompCommand.CONNECT) {
            jwtAuthenticationProvider.validateToken(jwtToken);
            log.info("Connect = {}", jwtToken);

        } else if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
            String simpleDestination = (String) message.getHeaders().get("simpleDestination");
            if (simpleDestination == null) {
                throw new RoomNotFoundException("존재하지 않는 방입니다.");
            }
            String roomId = chatService.getRoomId(simpleDestination);

            String simpSessionId = (String) message.getHeaders().get("simpSessionId");

            repository.mappingUserRoom(roomId, simpSessionId);
            chatService.messageResolver(new MessageRequestDto(Long.parseLong(roomId), MessageType.ENTER, ""), jwtToken);
            log.info("SUBSCRIBE {}, {}", simpSessionId, roomId);
        }else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료

            // 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            //나갈떄 redis 맵에서 roomId와 sessionId의 매핑을 끊어줘야 하기때문에 roomId찾고
            String roomId = repository.getUserEnterRoomId(sessionId);

            // 퇴장한 클라이언트의 roomId 맵핑 정보를 삭제한다.
            repository.removeUserEnterInfo(sessionId);
            log.info("DISCONNECT {}, {}", sessionId, roomId);
        }
        return message;
    }

}


