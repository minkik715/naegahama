package com.hanghae.naegahama.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchAnswerRequest {

    private List<SearchRequest> searchRequest;
    private Long postCount;

    public SearchAnswerRequest(List<SearchRequest> searchRequest, Long postCount) {
        this.searchRequest = searchRequest;
        this.postCount = postCount;
    }
}
