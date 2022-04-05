package com.hanghae.naegahama.repository.postlikerepository;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;


import static com.hanghae.naegahama.domain.QPostLike.postLike;

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
