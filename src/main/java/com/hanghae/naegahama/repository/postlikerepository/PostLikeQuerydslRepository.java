package com.hanghae.naegahama.repository.postlikerepository;

import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.domain.QPostLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.hanghae.naegahama.domain.QAnswerLike.answerLike;
import static com.hanghae.naegahama.domain.QPostLike.*;

@Repository
public class PostLikeQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostLikeQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Long countPostLikes(Long postId){
        return queryFactory
                .select(postLike.countDistinct())
                .where(postLike.post.id.eq(postId))
                .from(postLike)
                .fetchOne();
    }

    public Optional<PostLike> findPostLikeByUserPost(Long userId, Long postId){
        return Optional.ofNullable(
                queryFactory
                        .select(postLike)
                        .where(postLike.post.id.eq(postId)
                                .and(postLike.user.id.eq(userId)))
                        .from(postLike)
                        .fetchOne()
        );
    }

}
