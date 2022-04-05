package com.hanghae.naegahama.repository.commentrepository;

import com.hanghae.naegahama.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

import javax.persistence.EntityManager;
import java.util.List;

import static com.hanghae.naegahama.domain.QComment.*;
<<<<<<< HEAD
=======
import static com.hanghae.naegahama.domain.QComment.comment;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

@Repository
public class CommentQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CommentQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Long countCommentByAnswer(Long answerId){
        return queryFactory
                .select(comment.countDistinct())
                .where(comment.answer.id.eq(answerId))
                .from(comment)
                .fetchOne();
    }

    public List<Comment> findCommentsByParentId(Long id){
        return queryFactory
                .select(comment)
                .distinct()
                .where(comment.parentComment.id.eq(id))
                .from(comment)
                .fetch();
    }

    public Long countCommentsByParentId(Long id){
        return queryFactory
                .select(comment.countDistinct())
                .where(comment.parentComment.id.eq(id))
                .from(comment)
                .fetchOne();
    }

    public List<Comment> findCommentByAnswer(Long answerId){
        return queryFactory
                .select(comment)
                .distinct()
                .where(comment.answer.id.eq(answerId)
                        .and(comment.parentComment.isNotNull()))
                .from(comment)
                .orderBy(comment.modifiedAt.desc())
                .fetch();
    }



}
