package com.hanghae.naegahama.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchRequest {

    private Long id;
    private String title;
    private String content;
    private String file;
    private LocalDateTime modifiedAt;

    public SearchRequest(Long id, String title, String content,
                         LocalDateTime modifiedAt, String file) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.file =file;
        this.modifiedAt = modifiedAt;
    }

    public void setFile(String file) {
        this.file = file;
    }
}