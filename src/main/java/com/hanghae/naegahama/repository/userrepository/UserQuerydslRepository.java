package com.hanghae.naegahama.repository.userrepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.QUser;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.domain.UserComment;
=======
import com.hanghae.naegahama.domain.User;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.hanghae.naegahama.domain.QUser.user;
<<<<<<< HEAD
import static com.hanghae.naegahama.domain.QUserComment.userComment;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

@Repository
public class UserQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<User> findUserByNickanme(String nickname){
        return Optional.ofNullable(queryFactory
                .select(user)
                .where(user.nickName.eq(nickname))
                .from(user)
                .fetchOne());
    }

    //Optional<User> findByKakaoId(Long KakaoId);
    public Optional<User> findUserByKakaoId(Long kakaoId){
        return Optional.ofNullable(queryFactory
                .select(user)
                .where(user.kakaoId.eq(kakaoId))
                .from(user)
                .fetchOne());
    }

    public Optional<User> findUserByEmail(String email){
        return Optional.ofNullable(queryFactory
                .select(user)
                .where(user.email.eq(email))
                .from(user)
                .fetchOne());
    }

    public List<User> findTop5UserByPoint(){
        return queryFactory
                .select(user)
                .orderBy(user.point.asc())
                .limit(5)
                .from(user)
                .fetch();
    }




    //List<User> findTop5ByOrderByPointDesc();

//    Optional<User> findByEmail(String email);

}

