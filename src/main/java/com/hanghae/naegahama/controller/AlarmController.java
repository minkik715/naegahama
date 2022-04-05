package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.alarm.AlarmDto;
import com.hanghae.naegahama.dto.alarm.AlarmResponseDto;
import com.hanghae.naegahama.dto.alarm.CountAlarmDto;
<<<<<<< HEAD
import com.hanghae.naegahama.service.AlarmService;
import com.hanghae.naegahama.security.UserDetailsImpl;
=======
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.AlarmService;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlarmController {


    private final AlarmService alarmService;


    //알람 전체조회
    @GetMapping("/api/alarm")
    public List<AlarmResponseDto> getAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return alarmService.getAlarm(userDetails);
    }


    //알람 리딩 안된 알람갯수 조회.
    @GetMapping("/api/alarmCount")
    public CountAlarmDto countAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return alarmService.countAlarm(userDetails);
    }

    //알람 리딩 전체 확인
    @PostMapping("/api/alarm")
    public List<AlarmDto> readAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return alarmService.readAlarm(userDetails);
    }


    //알람 삭제
    @DeleteMapping("/api/alarm/{alarmId}")
    public BasicResponseDto deleteAlarm(@PathVariable Long alarmId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return alarmService.deleteAlarm(alarmId, userDetails);
    }

    //알람 전체삭제
    @DeleteMapping("/api/alarm")
    public BasicResponseDto deleteAllAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return alarmService.deleteAllAlarm(userDetails);
    }



}

//    @DestinationVariable은 @PathVariable이랑 비슷하게 생각하시면 됩니다.
//        /ws-stomp로 소켓을 연결하면, 클라이언트에서는 /sub/{userId}를 구독할 것입니다.
//        클라이언트에서 /ws-stomp로 요청하면 소켓이 연결되는 것이라 생각하면 됩니다.
