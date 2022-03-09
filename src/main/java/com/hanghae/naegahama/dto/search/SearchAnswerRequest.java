package com.hanghae.naegahama.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchAnswerRequest {

    private List<SearchRequest> searchRequest;
    private Integer postCount;

    public SearchAnswerRequest(List<SearchRequest> searchRequest, Integer postCount) {
        this.searchRequest = searchRequest;
        this.postCount = postCount;
    }
}
