package com.hanghae.naegahama.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void alarmByMessage(MessageDto messageDto) {
        messagingTemplate.convertAndSend("/sub/" + messageDto.getReceiverId(), messageDto);
    }
}



//- 내가 요청한 글에 답변이 남겼을때

//        - 만족도 평가를 해야할때 (마감 시간 후에)
//        - 만족도 평가(경험치) 받았을때
//        - 레벨 오를때
//        - 좋아요 받았을떄



//        소켓을 연결하며 클라이언트가 구독했던 곳으로 메세지를 전송해주면 됩니다.