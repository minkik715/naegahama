package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.search.SearchAnswerRequest;
import com.hanghae.naegahama.dto.search.SearchPostRequest;
import com.hanghae.naegahama.dto.search.SearchWords;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SearchCotroller {

    private final SearchService searchService;

    //요청글 검색결과.
    @GetMapping("/postsearch/{searchWord}")
    public SearchPostRequest postSearchList(@PathVariable String searchWord,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return searchService.postSearchList(searchWord, userDetails);
    }

    //답변글 검색결과.
    @GetMapping("/answersearch/{searchWord}")
    public SearchAnswerRequest answerSearchList(@PathVariable String searchWord, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return searchService.answerSearchList(searchWord, userDetails);
    }

    //최근검색어 순위.
    @GetMapping("/search")
    public List<SearchWords> SearchList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return searchService.SearchList(userDetails);
    }
    //검색어 삭제
    @DeleteMapping("/search/{searchId}")
    public BasicResponseDto deleteSearch(@PathVariable Long searchId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return searchService.deleteSearch(searchId, userDetails);
   }

    //검색어 전체삭제
    @DeleteMapping("/search")
    public BasicResponseDto deleteAllSearch(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return searchService.deleteAllSearch(userDetails);
    }


}