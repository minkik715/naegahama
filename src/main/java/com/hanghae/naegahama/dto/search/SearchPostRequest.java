package com.hanghae.naegahama.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchPostRequest {
    private List<SearchRequest> searchRequest;
    private Integer answerCount;

    public SearchPostRequest(List<SearchRequest> searchRequest, Integer answerCount) {
        this.searchRequest = searchRequest;
        this.answerCount = answerCount;
    }
}