package com.hanghae.naegahama.repository.rankrepository;

import com.hanghae.naegahama.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD
import javax.persistence.EntityManager;
import java.util.List;
=======

import javax.persistence.EntityManager;
import java.util.List;

>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import static com.hanghae.naegahama.domain.QRank.rank1;

@Repository
public class RankQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RankQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    public List<Rank> findRanks(){
        return queryFactory
                .select(rank1)
                .distinct()
                .orderBy(rank1.rank.asc())
                .from(rank1)
                .fetch();
    }
}
