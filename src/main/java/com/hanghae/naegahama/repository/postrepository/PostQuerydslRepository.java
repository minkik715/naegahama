package com.hanghae.naegahama.repository.postrepository;

import com.hanghae.naegahama.domain.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hanghae.naegahama.domain.QPost.*;
import static com.hanghae.naegahama.domain.QPost.post;

@Repository
public class PostQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    public List<Post> findPosts(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return getBasicposts(queryFactory)
                .fetch();
    }

    public List<Post> findPostsByCategory(String category){

        return getBasicposts(queryFactory)
                .where(post.category.eq(category))
                .fetch();
    }

    public List<Post> findAdminPost(UserRoleEnum userRoleEnum){

        return getBasicposts(queryFactory)
                .where(post.user.role.eq(userRoleEnum))
                .fetch();
    }
    public List<Post> findPostBySearchWord(String searchWord){

        return getBasicposts(queryFactory)
                .where(post.title.contains(searchWord)
                        .or(post.content.contains(searchWord)))
                .fetch();
    }

    public List<Post> findPostByHippoName(String hippoName){
        return queryFactory
                .select(post)
                .where(post.user.hippoName.eq(hippoName))
                .from(post)
                .fetch();
    }

    public List<Post> findPostByUser(Long userId){
        return getBasicposts(queryFactory)
                .where(post.user.id.eq(userId))
                .fetch();

    }

    public Long countBySearchWord(String searchWord){
        return queryFactory
                .select(post.countDistinct())
                .where(post.title.contains(searchWord)
                        .or(post.content.contains(searchWord)))
                .from(post)
                .fetchOne();
    }


    public Long countPostByUser(User paramUser){
        return queryFactory
                .select(post.countDistinct())
                .where(post.user.id.eq(paramUser.getId()))
                .from(post)
                .fetchOne();
    }


    private JPAQuery<Post> getBasicposts(JPAQueryFactory queryFactory) {
        return queryFactory
                .select(post)
                .distinct()
                .from(post)
                .orderBy(post.createdAt.desc())
                .leftJoin(post.answerList).fetchJoin()
                .leftJoin(post.user).fetchJoin();
    }
}
