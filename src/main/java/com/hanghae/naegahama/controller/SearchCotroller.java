package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SearchCotroller {

    private final SearchService searchService;

    //요청글 검색결과.
    @GetMapping("/postsearch/{searchWord}")
    public ResponseEntity<?> postSearchList(@PathVariable String searchWord,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(searchService.postSearchList(searchWord, userDetails));
    }

    //답변글 검색결과.
    @GetMapping("/answersearch/{searchWord}")
    public ResponseEntity<?> answerSearchList(@PathVariable String searchWord) {
        return ResponseEntity.ok().body(searchService.answerSearchList(searchWord));
    }

    //최근검색어 순위.
    @GetMapping("/search")
    public ResponseEntity<?> SearchList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(searchService.SearchList(userDetails));
    }
    //검색어 삭제
    @DeleteMapping("/search/{searchId}")
    public ResponseEntity<?> deleteSearch(@PathVariable Long searchId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(searchService.deleteSearch(searchId, userDetails));
   }

    //검색어 전체삭제
    @DeleteMapping("/search")
    public ResponseEntity<?> deleteAllSearch(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(searchService.deleteAllSearch(userDetails));
    }


}