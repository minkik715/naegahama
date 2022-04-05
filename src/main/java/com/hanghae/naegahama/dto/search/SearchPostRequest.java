package com.hanghae.naegahama.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchPostRequest {
    private List<SearchRequest> searchRequest;
    private Long answerCount;

    public SearchPostRequest(List<SearchRequest> searchRequest, Long answerCount) {
        this.searchRequest = searchRequest;
        this.answerCount = answerCount;
    }
}