package com.hanghae.naegahama.dto.search;

import com.hanghae.naegahama.util.TimeHandler;
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
    private String modifiedAt;
    private String category;
    public SearchRequest(Long id, String title, String content,
                         LocalDateTime modifiedAt, String file, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.file =file;
        this.modifiedAt = TimeHandler.setModifiedAtLIst(modifiedAt);
        this.category = category;
    }
}