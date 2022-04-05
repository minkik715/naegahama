package com.hanghae.naegahama.repository.alarmrepository;

import com.hanghae.naegahama.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hanghae.naegahama.domain.QAlarm.*;
<<<<<<< HEAD
=======
import static com.hanghae.naegahama.domain.QAlarm.alarm;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

@Repository
public class AlarmQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AlarmQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    //findAllByReceiverOrderByCreatedAtDesc

    public List<Alarm> findAlarmsByReceiver(Long userId){
        return queryFactory
                .select(alarm)
                .where(alarm.receiver.id.eq(userId))
                .orderBy(alarm.modifiedAt.desc())
                .from(alarm)
                .fetch();
    }

    //countByReadingStatusAndReceiver

    public Long countNotReadAlarm(Long userId){
        return queryFactory
                .select(alarm.countDistinct())
                .where(alarm.receiver.id.eq(userId)
                        .and(alarm.readingStatus.eq(ReadingStatus.N)))
                .from(alarm)
                .fetchOne();
    }
}
