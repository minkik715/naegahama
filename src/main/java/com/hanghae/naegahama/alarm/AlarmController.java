package com.hanghae.naegahama.alarm;

import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final AlarmService alarmService;

    // stomp 테스트 화면
    @GetMapping("/alarm/stomp")
    public String stompAlarm() {
        return "/stomp";
    }

    @MessageMapping("/{userId}")
    public void message(@DestinationVariable("userId") Long userId) {
        messagingTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
    }

    //알람 삭제
    @DeleteMapping("/api/alarm/{alarmId}")
    public ResponseEntity deleteAlarm(@PathVariable Long alarmId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(alarmService.deleteAlarm(alarmId, userDetails));
    }

    //알람 전체삭제
    @DeleteMapping("/api/alarm")
    public ResponseEntity<?> deleteAllAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(alarmService.deleteAllAlarm(userDetails));
    }

    //알람 전체조회
    @GetMapping("/api/alarm")
    public ResponseEntity<?> getAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(alarmService.getAlarm(userDetails));
    }

    //알람 리딩 안된 알람갯수 조회.
    @GetMapping("/api/alarmCount")
    public ResponseEntity countAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(alarmService.countAlarm(userDetails));
    }


    //알람 리딩 전체 확인
    @PostMapping("/api/alarm")
    public ResponseEntity<?> readAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body( alarmService.readAlarm(userDetails));
    }
}

//    @DestinationVariable은 @PathVariable이랑 비슷하게 생각하시면 됩니다.
//        /ws-stomp로 소켓을 연결하면, 클라이언트에서는 /sub/{userId}를 구독할 것입니다.
//        클라이언트에서 /ws-stomp로 요청하면 소켓이 연결되는 것이라 생각하면 됩니다.
