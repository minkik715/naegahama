package com.hanghae.naegahama.alarm;

import com.hanghae.naegahama.config.jwt.JwtAuthenticationProvider;
import com.hanghae.naegahama.security.jwt.JwtDecoder;
import com.hanghae.naegahama.security.jwt.JwtTokenUtils;
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
        }
        return message;
    }
}

//    preSend를 오버라이딩하여, CONNECT하는 상황이라면, 토큰을 검증해줍니다.
//        토큰이 유효하지 않다면, 예외를 발생시켜줄 것입니다.
//        토큰 검증하는 코드는 예제가 많으므로 생략하겠습니다.