package com.hanghae.naegahama.dto.search;

import com.hanghae.naegahama.domain.Search;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import java.time.LocalDateTime;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
@Getter
@NoArgsConstructor
public class SearchWords {

    private Long id;
    private String searchWord;
    private String modifiedAt;

    public SearchWords(Search search) {
        this.id = search.getId();
        this.searchWord = search.getSearchWord();
        this.modifiedAt = TimeHandler.setModifiedAtAnswerRecentSearch(search.getModifiedAt());
    }
}
