package com.hanghae.naegahama.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchAnswerRequestDto {

    private Long id;
    private String title;
    private String content;
    private String file;
    private LocalDateTime modifiedAt;
    private Long answerCount;


    public SearchAnswerRequestDto(Long id, String title, String content, String file,
                                  LocalDateTime modifiedAt, Long answerCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.file = file;
        this.modifiedAt = modifiedAt;
        this.answerCount = answerCount;

    }
}
