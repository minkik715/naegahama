package com.hanghae.naegahama.repository.answerrepository;
import com.hanghae.naegahama.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import static com.hanghae.naegahama.domain.QAnswer.answer;

@Repository
public class AnswerQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AnswerQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
   //"select distinct a from Answer a left join fetch a.user left join fetch a.answerVideo left join fetch a.likeList"

    public List<Answer> findAnswersByPostId(Long postId){
          return queryFactory
                  .select(answer)
                  .where(answer.post.id.eq(postId))
                  .distinct()
                  .from(answer)
                  .leftJoin(answer.user).fetchJoin()
                  .leftJoin(answer.likeList).fetchJoin()
                  .orderBy(answer.modifiedAt.desc())
                  .fetch();
    }

    public List<Answer> findAnswersByUser(Long userId){
        return queryFactory
                .select(answer)
                .where(answer.user.id.eq(userId))
                .distinct()
                .from(answer)
                .leftJoin(answer.likeList).fetchJoin()
                .orderBy(answer.modifiedAt.desc())
                .fetch();
    }
    //countByUserAndPost_CategoryAndStarGreaterThanEqual
    public Long countByUserPostCate(Long userId, String category){
        return queryFactory
                .select(answer.countDistinct())
                .where(answer.user.id.eq(userId)
                        .and(answer.post.category.eq(category))
                        .and(answer.star.goe(4)))
                .from(answer)
                .fetchOne();
    }

    public Long countAnswerByUser(User paramUser){
        return queryFactory
                .select(answer.countDistinct())
                .where(answer.user.id.eq(paramUser.getId()))
                .from(answer)
                .fetchOne();
    }

    public List<Answer> findAnswerBySearchWord(String searchWord){
        return queryFactory
                .select(answer)
                .where(answer.title.contains(searchWord)
                        .or(answer.content.contains(searchWord)))
                .from(answer)
                .leftJoin(answer.post).fetchJoin()
                .leftJoin(answer.fileList).fetchJoin()
                .fetch();
    }

    public Long countBySearchWord(String searchWord){
        return queryFactory
                .select(answer.countDistinct())
                .where(answer.title.contains(searchWord)
                        .or(answer.content.contains(searchWord)))
                .from(answer)
                .fetchOne();
    }

}
