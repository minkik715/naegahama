package com.hanghae.naegahama.repository.userpagecommentrepository;

import com.hanghae.naegahama.domain.UserComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hanghae.naegahama.domain.QUserComment.userComment;

@Repository
public class UserCommentQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserCommentQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<UserComment> findUCommentByUser(Long userId){
        return queryFactory
                .select(userComment)
                .where(userComment.pageUser.id.eq(userId))
                .from(userComment)
                .fetch();
    }

    //findByParentCommentIdOrderByModifiedAt
    public List<UserComment> findUCommentByParentId(Long parentId){
        return queryFactory
                .select(userComment)
                .where(userComment.parentComment.id.eq(parentId))
                .from(userComment)
                .fetch();
    }
}
