package com.hanghae.naegahama.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchPostRequestDto {
    private Long id;
    private String title;
    private String content;
    private String file;
    private LocalDateTime modifiedAt;
    private Long postCount;


    public SearchPostRequestDto(Long id, String title, String content, String file,
                                LocalDateTime modifiedAt, Long postCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.file = file;
        this.modifiedAt = modifiedAt;
        this.postCount = postCount;

    }
}