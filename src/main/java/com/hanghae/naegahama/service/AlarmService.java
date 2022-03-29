
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.dto.alarm.AlarmDto;
import com.hanghae.naegahama.dto.alarm.AlarmResponseDto;
import com.hanghae.naegahama.dto.alarm.CountAlarmDto;
import com.hanghae.naegahama.domain.Alarm;
import com.hanghae.naegahama.domain.ReadingStatus;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.repository.AlarmRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    private final AlarmRepository alarmRepository;

    public void alarmByMessage(AlarmResponseDto AlarmResponseDto) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), AlarmResponseDto);
    }

    //알람 삭제
    @Transactional
    public BasicResponseDto deleteAlarm(Long alarmId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        alarmRepository.deleteByAlarmIdAndReceiver(alarmId, user);
        return new BasicResponseDto("success");
    }

    //알람 전체 삭제
    @Transactional
    public BasicResponseDto deleteAllAlarm(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        alarmRepository.deleteByReceiver(user);
        return new BasicResponseDto("success");
    }


    //알람 전체 조회
    public List<AlarmResponseDto> getAlarm(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Alarm> alarms = alarmRepository.findAllByReceiverOrderByCreatedAtDesc(user);
        List<AlarmResponseDto> response = new ArrayList<>();


        for (Alarm alarm : alarms) {

            response.add(new AlarmResponseDto(alarm));
        }
        return response;
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
            alarm.changeReadingStatus(ReadingStatus.Y);
            alarmDtos.add(new AlarmDto(alarm));
        }
        return alarmDtos;
    }
}
