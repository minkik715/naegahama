
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.dto.alarm.AlarmDto;
import com.hanghae.naegahama.dto.alarm.AlarmResponseDto;
import com.hanghae.naegahama.dto.alarm.CountAlarmDto;
import com.hanghae.naegahama.domain.ReadingStatus;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.repository.alarmrepository.AlarmQuerydslRepository;
import com.hanghae.naegahama.repository.alarmrepository.AlarmRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    private final AlarmRepository alarmRepository;

    private final AlarmQuerydslRepository alarmQuerydslRepository;
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
        alarmRepository.deleteByReceiver(userDetails.getUser());
        return new BasicResponseDto("success");
    }


    //알람 전체 조회
    public List<AlarmResponseDto> getAlarm(UserDetailsImpl userDetails) {
        return alarmQuerydslRepository.findAlarmsByReceiver(userDetails.getUser().getId()).stream()
                .map(AlarmResponseDto::new)
                .collect(Collectors.toList());

    }


    //리딩 안된 알람갯수 카운트.
    public CountAlarmDto countAlarm(UserDetailsImpl userDetails) {
        Long alarmCount = alarmRepository.countByReadingStatusAndReceiver(ReadingStatus.N, userDetails.getUser());
        return new CountAlarmDto(
                alarmCount
        );
    }

    //리딩 전체 확인하는 요청.
    @Transactional
    public List <AlarmDto> readAlarm(UserDetailsImpl userDetails) {
        return alarmQuerydslRepository.findAlarmsByReceiver(userDetails.getUser().getId())
                .stream().map(
                        AlarmDto::new)
                .collect(Collectors.toList());
    }
}
