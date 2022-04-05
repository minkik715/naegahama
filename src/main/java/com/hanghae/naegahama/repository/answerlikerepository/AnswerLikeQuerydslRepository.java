package com.hanghae.naegahama.repository.answerlikerepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import static com.hanghae.naegahama.domain.QAnswerLike.answerLike;

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
