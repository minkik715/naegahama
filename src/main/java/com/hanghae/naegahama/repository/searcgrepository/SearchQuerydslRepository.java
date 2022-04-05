package com.hanghae.naegahama.repository.searcgrepository;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.QSearch;
import com.hanghae.naegahama.domain.Rank;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.domain.Search;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

<<<<<<< HEAD
import static com.hanghae.naegahama.domain.QRank.rank1;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import static com.hanghae.naegahama.domain.QSearch.search;

@Repository
public class SearchQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public SearchQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
//    findAllByUserOrderByCreatedAtDesc
    public List<Search> findSearchByUser(Long userId){
        return queryFactory
                .select(search)
                .where(search.user.id.eq(userId))
                .orderBy(search.modifiedAt.desc())
                .from(search)
                .fetch();
    }

//    existsBySearchWordAndUser

    public Boolean existSearchByUserWord(Long userId, String word){
        return queryFactory
                .select(search)
                .where(search.searchWord.eq(word)
                        .and(search.user.id.eq(userId)))
<<<<<<< HEAD
=======
                .from(search)
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
                .fetchFirst() != null;

    }
}
