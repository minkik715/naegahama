package com.hanghae.naegahama.repository.answerlikerepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.AnswerFile;
import com.hanghae.naegahama.domain.QAnswerLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hanghae.naegahama.domain.QAnswerFile.answerFile;
import static com.hanghae.naegahama.domain.QAnswerLike.*;
=======
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.hanghae.naegahama.domain.QAnswerLike.*;
import static com.hanghae.naegahama.domain.QAnswerLike.answerLike;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

@Repository
public class AnswerLikeQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AnswerLikeQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Long countAnsLikes(Long answerId){
        return queryFactory
                .select(answerLike.countDistinct())
                .where(answerLike.answer.id.eq(answerId))
                .from(answerLike)
                .fetchOne();
    }

}
