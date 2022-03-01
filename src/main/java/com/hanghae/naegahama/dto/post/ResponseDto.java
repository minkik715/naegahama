package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime modifiedAt;
    private Integer answerCount;
    private  Long user_id;
    private  String nickname;

    public ResponseDto(Long id, String title, String content, LocalDateTime modifiedAt ,
                       Integer answerCount, Long user_id,String nickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.answerCount = answerCount;
        this.user_id = user_id;
        this.nickname = nickname;
    }
}