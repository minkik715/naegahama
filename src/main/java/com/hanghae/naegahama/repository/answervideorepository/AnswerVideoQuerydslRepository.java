package com.hanghae.naegahama.repository.answervideorepository;

import com.hanghae.naegahama.domain.AnswerVideo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.hanghae.naegahama.domain.QAnswerVideo.answerVideo;

@Repository
public class AnswerVideoQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AnswerVideoQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    //findAllByAnswerOrderByCreatedAt
    //findAllByUrlEndingWithOrUrlEndingWithOrderByCreatedAtDesc( String ext, String ext2)
    public List<AnswerVideo> findVideosByExt(String ext, String ext2 ){
        return queryFactory
                .select(answerVideo)
                .distinct()
                .where(answerVideo.url.endsWith(ext)
                        .or(answerVideo.url.endsWith(ext2)))
                .from(answerVideo)
                .leftJoin(answerVideo.answer).fetchJoin()
                .fetch();
    }

    public Optional<AnswerVideo> findVideoByAnswer(Long answerId){
        return Optional.ofNullable(queryFactory
                .select(answerVideo)
                .where(answerVideo.answer.id.eq(answerId))
                .from(answerVideo)
                .fetchOne());
    }



}
