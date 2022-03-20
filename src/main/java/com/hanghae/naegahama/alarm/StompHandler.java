package com.hanghae.naegahama.alarm;

import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.repository.UserRepository;
import com.hanghae.naegahama.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;
    private final RedisRepository redisRepository;
    private final UserRepository userRepository;


    //Controller에 가기전에 이곳을 먼저 들리게 된다. 그것이 인터셉터의 역할
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // HTTP의 Request Response처럼
        // WebSocket은 message와 channel을 갖게된다.
        // accessor 을 이용하면 내용에 패킷에 접근할 수 있게된다.
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // 접근했을때 COMMAND HEADER의 값을 확인 한다.
        // 만약 CONNET라면 -> 초기 연결임
        if(accessor.getCommand() == StompCommand.CONNECT) {
            // websocket 연결요청
            // 토큰의 값만 확인 (로그인 여부를 확인하기 위함)
            // 헤더의 토큰값을 빼오기
            if(!jwtDecoder.isValidToken(accessor.getFirstNativeHeader("token")).isPresent())
                throw new AccessDeniedException("");
            log.info("connect 성공");
        }else if(accessor.getCommand() == StompCommand.SUBSCRIBE){
            String token = accessor.getFirstNativeHeader("token");
            log.info("token = {}", token);
            String username = jwtDecoder.decodeUsername(token);
            log.info("userId = {} username = {}", token, username);
            Optional<User> byEmail = userRepository.findByNickName(username);
            String id = String.valueOf(byEmail.get().getId());
            log.info(id);

            String sessionId = (String) message.getHeaders().get("simpSessionId");
            redisRepository.setSessionAlarm(sessionId,id);
            String userId1 = redisRepository.getSessionUserId(sessionId);
            log.info("SUBSCRIBED {}, {}", userId1, sessionId);
        }
        else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료

            // 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.

            // 클라이언트 퇴장 메시지를 채팅방에 발송한다.(redis publish)
            String token = accessor.getFirstNativeHeader("token");
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            redisRepository.removeUserEnterInfo(sessionId);
            log.info("DISCONNECT  {}", sessionId);
        }
        return message;
    }
}

