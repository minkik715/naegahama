package com.hanghae.naegahama.dto.answer;

import com.hanghae.naegahama.domain.Answer;
<<<<<<< HEAD
import com.hanghae.naegahama.domain.User;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import java.time.LocalDateTime;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
@Getter
@NoArgsConstructor

public class AnswerGetResponseDto
{
    private Long answerId;

    private String answerWriter;

    private String title;

    private String modifiedAt;

    private int answerLikeCount;

    private int commentCount;

    private int imageCount;

    private String imgUrl;
    
    private int star;
    
    private Long userId;

    public AnswerGetResponseDto(Answer answer)
    {
        this.answerId = answer.getId();
        this.answerWriter = answer.getUser().getNickName();
        this.title = answer.getTitle();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(answer.getModifiedAt());
        this.commentCount = answer.getCommentList().size();
        this.answerLikeCount = answer.getLikeList().size();
        this.imageCount = answer.getFileList().size();
        this.star = answer.getStar();
        this.imgUrl = answer.getUser().getHippoImage();
        this.userId = answer.getUser().getId();
    }
}
