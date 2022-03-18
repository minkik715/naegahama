
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Alarm;
import com.hanghae.naegahama.dto.alarm.*;
import com.hanghae.naegahama.repository.AlarmRepository;
import com.hanghae.naegahama.dto.alarm.ReadingStatus;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final SimpMessageSendingOperations messagingTemplate;
    private final AlarmRepository alarmRepository;

    public void alarmByMessage(MessageDto messageDto) {
        messagingTemplate.convertAndSend("/sub/" + messageDto.getReceiverId(), messageDto);
    }

    //알람 삭제
    @Transactional
    public ResponseEntity deleteAlarm(Long alarmId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        alarmRepository.deleteByAlarmIdAndReceiver(alarmId, user);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    //알람 전체 삭제
    @Transactional
    public ResponseEntity<?> deleteAllAlarm(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        alarmRepository.deleteByReceiver(user);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    //알람 전체 조회
    public List<AlarmResponseDto> getAlarm(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Alarm> alarms = alarmRepository.findAllByReceiverOrderByCreatedAtDesc(user);
        List<AlarmResponseDto> response = new ArrayList<>();


        for (Alarm alarm : alarms) {

            String timeSet = timeSet(alarm);

            AlarmResponseDto alarmResponseDto = new AlarmResponseDto(
                    alarm.getAlarmId(),
                    alarm.getSenderNickName(),
                    alarm.getType(),
                    alarm.getId(),
                    alarm.getTitle(),
                    alarm.getModifiedAt(),
                    alarm.getReadingStatus(),
                    timeSet
            );
            response.add(alarmResponseDto);
        }
        return response;
    }


    //전체 조회 할때 보여주는 시간.
    private String timeSet(Alarm alarm) {
        String timeSet = "";
        LocalDateTime modifiedAt = alarm.modifiedAt();
        if (modifiedAt == null) {
            timeSet = "기한 없음";
        } else {
            long minutes = 61;
            if (modifiedAt.isBefore(LocalDateTime.now())) {
            } else {
                minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), modifiedAt);
                int hour = (int) (minutes / 60);
                minutes = minutes % 60;
                timeSet = "마감 " + hour + "시간 " + minutes + "분 전";

                if (hour < 1) {
                    minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), modifiedAt);
                    timeSet = "마감 " + minutes + "분 전";

                }
                if (minutes < 1) {
                    long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), modifiedAt);
                    timeSet = "마감 " + seconds + "초 전";
                }
            }
        }
        return timeSet;
    }

    //리딩 안된 알람갯수 카운트.
    public CountAlarmDto countAlarm(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Long alarmCount = alarmRepository.countByReadingStatusAndReceiver(ReadingStatus.N, user);

        CountAlarmDto countAlarmDto = new CountAlarmDto(
                alarmCount
        );

        return countAlarmDto;
    }

    //리딩 전체 확인하는 요청.
    @Transactional
    public List <AlarmDto> readAlarm(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List <AlarmDto> alarmDtos = new ArrayList<>();
        List <Alarm> alarms = alarmRepository.findAllByReceiver(user);

        for (Alarm alarm : alarms) {
            AlarmDto alarmDto = new AlarmDto(alarm);
            alarm.changeReadingStatus(ReadingStatus.Y);
            alarmDtos.add(alarmDto);
        }
        return alarmDtos;
    }
}

//    레디스 연결하기 완성하기.
// 타임세트
//        - 만족도 평가를 해야할때 (마감 시간 후에) (미확인)
//        - 만족도 평가(경험치) 받았을때 (미확인)
//        - 레벨 오를때 (미확인)


//    기능 6 , 알람 8
//        - 내가 요청한 글에 답변이 남겼을때 answer
//        - 답변글에 댓글을 남긴경우 comment
//        - 댓글에 대댓글이 남겼을떄 child
//        - 만족도 평가를 해야할때 (마감 시간 후에) rate
//        - 만족도 평가(경험치) 받았을때 rated
//        - 레벨 오를때 level
//        - 요청글 좋아요 받았을떄 likeP
//        - 답변글 좋아요 받았을떄 likeA


//        소켓을 연결하며 클라이언트가 구독했던 곳으로 메세지를 전송해주면 됩니다.*/
