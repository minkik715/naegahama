package com.hanghae.naegahama.dto.search;

import com.hanghae.naegahama.domain.Search;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchWords {

    private Long id;
    private String searchWord;
    private LocalDateTime modifiedAt;

    public SearchWords(Search search) {
        this.id = search.getId();
        this.searchWord = search.getSearchWord();
        this.modifiedAt = search.getModifiedAt();
    }
}
