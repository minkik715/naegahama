package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.ex.UserNotFoundException;
import com.hanghae.naegahama.repository.RedisRepository;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.repository.userrepository.UserRepository;
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
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(!jwtDecoder.isValidToken(accessor.getFirstNativeHeader("token")).isPresent())
                throw new AccessDeniedException("");
        }else if(accessor.getCommand() == StompCommand.SUBSCRIBE){
            String token = accessor.getFirstNativeHeader("token");
            String username = jwtDecoder.decodeUsername(token);
            User byEmail = userRepository.findByEmail(username).orElseThrow(
                    () -> new UserNotFoundException("유저가 존재하지 않습니다.")
            );
            String id = null;
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            redisRepository.setSessionAlarm(sessionId,id);
            String userId1 = redisRepository.getSessionUserId(sessionId);
        }
        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            redisRepository.removeUserEnterInfo(sessionId);
        }
        return message;
    }
}

