package com.hanghae.naegahama.dto.search;

import com.hanghae.naegahama.domain.Answer;
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
    public SearchRequest(Answer answer) {
        this.id = answer.getId();
        this.title = answer.getTitle();
        this.content = answer.getContent();
        this.file =answer.getFileList().get(0).getUrl();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(answer.getModifiedAt());
        this.category = answer.getPost().getCategory();
    }
    public SearchRequest(Answer answer, String emptyFile) {
        this.id = answer.getId();
        this.title = answer.getTitle();
        this.content = answer.getContent();
        this.file =emptyFile;
        this.modifiedAt = TimeHandler.setModifiedAtLIst(answer.getModifiedAt());
        this.category = answer.getPost().getCategory();
    }
}