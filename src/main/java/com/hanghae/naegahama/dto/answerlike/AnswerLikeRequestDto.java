package com.hanghae.naegahama.dto.answerlike;

import com.hanghae.naegahama.domain.Answer;
<<<<<<< HEAD
import com.hanghae.naegahama.domain.Post;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnswerLikeRequestDto {
    private User user;
    private Answer answer;
}
