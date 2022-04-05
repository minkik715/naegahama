package com.hanghae.naegahama.repository.postrepository;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.QPost;
import com.hanghae.naegahama.domain.QUser;
import com.hanghae.naegahama.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;

import java.util.List;

import static com.hanghae.naegahama.domain.QPost.*;
import static com.hanghae.naegahama.domain.QUser.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostQuerydslRepositoryTest {

    @Autowired
    EntityManager em;


    @Test
    void findUser(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Post> posts = queryFactory
                .select(post)
                .from(post)
                .leftJoin(post.answerList).fetchJoin()
                .fetch();
    }

}