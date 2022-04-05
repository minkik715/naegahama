package com.hanghae.naegahama.repository.postfilerepository;

import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.PostFile;
import com.hanghae.naegahama.domain.QPostFile;
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
