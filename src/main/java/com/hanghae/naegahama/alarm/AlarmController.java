//package com.hanghae.naegahama.alarm;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class AlarmController {
//
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    // stomp 테스트 화면
//    @GetMapping("/alarm/stomp")
//    public String stompAlarm() {
//        return "/stomp";
//    }
//
//    @MessageMapping("/{userId}")
//    public void message(@DestinationVariable("userId") Long userId) {
//        messagingTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
//    }
//}
//
////    @DestinationVariable은 @PathVariable이랑 비슷하게 생각하시면 됩니다.
////        /ws-stomp로 소켓을 연결하면, 클라이언트에서는 /sub/{userId}를 구독할 것입니다.
////        클라이언트에서 /ws-stomp로 요청하면 소켓이 연결되는 것이라 생각하면 됩니다.