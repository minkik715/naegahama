package com.hanghae.naegahama.repository.postfilerepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.PostFile;
import com.hanghae.naegahama.domain.QPostFile;
=======
import com.hanghae.naegahama.domain.PostFile;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hanghae.naegahama.domain.QPostFile.postFile;

@Repository
public class PostFileQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostFileQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<PostFile> findFileByPost(Long postId){
        return queryFactory
                .select(postFile)
                .distinct()
                .where(postFile.post.id.eq(postId))
                .orderBy(postFile.modifiedAt.desc())
                .from(postFile)
                .fetch();
    }


}
