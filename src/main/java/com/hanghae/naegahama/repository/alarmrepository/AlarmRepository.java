package com.hanghae.naegahama.repository.alarmrepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.ReadingStatus;
import com.hanghae.naegahama.domain.Alarm;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

=======
import com.hanghae.naegahama.domain.Alarm;
import com.hanghae.naegahama.domain.ReadingStatus;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
public  interface AlarmRepository extends JpaRepository<Alarm, Long> {
    //알람 전체 삭제
    void deleteByReceiver(User user);
    //알람 삭제
    void deleteByAlarmIdAndReceiver(Long alarmId, User user);

//    List<Alarm> findAllByReceiverOrderByCreatedAtDesc(User user);

    //
//    List<Alarm>findAllByReceiver(User user);

//알람 갯수 파악
    Long countByReadingStatusAndReceiver(ReadingStatus N, User user);
}