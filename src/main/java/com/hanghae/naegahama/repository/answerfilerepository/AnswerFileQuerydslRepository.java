package com.hanghae.naegahama.repository.answerfilerepository;

import com.hanghae.naegahama.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;

import static com.hanghae.naegahama.domain.QAnswerFile.*;
=======

import static com.hanghae.naegahama.domain.QAnswerFile.*;
import static com.hanghae.naegahama.domain.QAnswerFile.answerFile;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

@Repository
public class AnswerFileQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AnswerFileQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    //findAllByAnswerOrderByCreatedAt
    public List<AnswerFile> findFilesByAnswer(Long answerId){
        return queryFactory
                .select(answerFile)
                .where(answerFile.answer.id.eq(answerId))
                .orderBy(answerFile.modifiedAt.desc())
                .from(answerFile)
                .fetch();
    }



}
