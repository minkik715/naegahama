package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.search.SearchAnswerRequest;
import com.hanghae.naegahama.dto.search.SearchPostRequest;
import com.hanghae.naegahama.dto.search.SearchRequest;
import com.hanghae.naegahama.handler.ex.PostNotFoundException;
import com.hanghae.naegahama.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@EnableAutoConfiguration
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class SearchService {

    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final SearchRepository searchRepository;


//    //검색키워드 유저정보에 저장.
//    public List<SearchPostRequestDto> postSearchList(String searchWord, UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        Search search = new Search(searchWord, user);
//        searchRepository.save(search);

    //검색키워드 유저정보에 저장.
    public SearchPostRequest postSearchList(String searchWord, UserDetailsImpl userDetails) {

        if (userDetails == null) {
        } else {
            User user = userDetails.getUser();
            Search search = new Search(searchWord, user);
            searchRepository.save(search);
        }

        //요청글 검색.
        List<Post> posts = postRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord, searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Integer answerCount = answerRepository.countByContentContainingOrTitleContaining(searchWord, searchWord);
        if (posts == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
        for (Post post : posts) {
            SearchRequest searchRequest = new SearchRequest(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getFileList().get(0).getUrl(),
                    post.getModifiedAt()
            );
            searchRequests.add(searchRequest);
        }

        SearchPostRequest searchPostRequest = new SearchPostRequest(
                searchRequests,
                answerCount
        );
        return searchPostRequest;
    }


    //답변글 검색
    public SearchAnswerRequest answerSearchList(String searchWord) {
        List<Answer> Answers = answerRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord, searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Integer postCount = postRepository.countByContentContainingOrTitleContaining(searchWord, searchWord);
        if (Answers == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
        for (Answer Answer : Answers) {
            SearchRequest searchRequest = new SearchRequest(
                    Answer.getId(),
                    Answer.getTitle(),
                    Answer.getContent(),
                    Answer.getFileList().get(0).getUrl(),
                    Answer.getModifiedAt()
            );
            searchRequests.add(searchRequest);
        }

        SearchAnswerRequest searchAnswerRequest = new SearchAnswerRequest(
                searchRequests,
                postCount
        );
        return searchAnswerRequest;
    }
}


//    //최근검색어 순위
//    public List<SearchAnswerRequestDto> SearchList() {
//        searchRepository.여기에서 가장 많이 검색된 순위 5개? 정도 꺼내서 보여주기.
//
//}


