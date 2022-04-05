package com.hanghae.naegahama.repository.userpagecommentrepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.QUserComment;
import com.hanghae.naegahama.domain.Search;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.domain.UserComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

<<<<<<< HEAD
import static com.hanghae.naegahama.domain.QSearch.search;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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
<<<<<<< HEAD
                .leftJoin(userComment.writer).fetchJoin()
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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
