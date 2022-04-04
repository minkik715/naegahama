package com.hanghae.naegahama.repository.achievementrepository;

import com.hanghae.naegahama.domain.Achievement;
import com.hanghae.naegahama.domain.QUser;
import com.hanghae.naegahama.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hanghae.naegahama.domain.QAchievement.achievement;
import static com.hanghae.naegahama.domain.QUser.user;

@Repository
public class AchieveQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AchieveQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Achievement findAchievementByUser(User paramUser){
        return queryFactory
                .select(user.achievement)
                .where(user.id.eq(paramUser.getId()))
                .from(user)
                .fetchOne();
    }


}
