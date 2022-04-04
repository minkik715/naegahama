
package com.hanghae.naegahama.config;

import com.hanghae.naegahama.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler; // jwt 토큰 인증 핸들러

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")  // url/alarm 웹 소켓 연결 주소
                .setAllowedOrigins("https://www.i-hama.xyz/","https://i-hama.xyz","http://localhost:3000")
                .withSockJS();  //sock.js를 통하여 낮은 버전의 브라우저에서도 websocket 이 동작할수 있게 한다
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // prefix /sub 로 수신 메시지 구분
        //여기로 데이터가 들어온 경우 messageMapping 으로 JUMP
        registry.setApplicationDestinationPrefixes("/pub"); // prefix /pub 로 발행 요청
    }

    // StompHandler 인터셉터 설정
    // StompHandler 가 Websocket 앞단에서 token 및 메시지 TYPE 등을 체크할 수 있도록 다음과 같이 인터셉터로 설정한다
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler); // 핸들러 등록
    }
}

///ws-stomp로 소켓을 연결하고, /sub/... 을 구독하고 있으면, 메세지를 전송할 수 있습니다.
//        토큰을 인증하기 위한 stompHandler를 추가해줍니다. 연결이 되기 전에 해당 핸들러의 메소드를 실행할 것입니다.*/
