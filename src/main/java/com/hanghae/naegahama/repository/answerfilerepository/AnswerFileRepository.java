package com.hanghae.naegahama.repository.answerfilerepository;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerFile;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
public interface AnswerFileRepository extends JpaRepository<AnswerFile,Long> {
    void deleteByAnswer(Answer answer);


    //List<AnswerFile> findAllByAnswerOrderByCreatedAt(Answer answer);
}
