package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.event.SearchWordEvent;
import com.hanghae.naegahama.dto.search.SearchAnswerRequest;
import com.hanghae.naegahama.dto.search.SearchPostRequest;
import com.hanghae.naegahama.dto.search.SearchRequest;
import com.hanghae.naegahama.dto.search.SearchWords;
import com.hanghae.naegahama.ex.SearchNotFoundException;
import com.hanghae.naegahama.repository.answerrepository.AnswerQuerydslRepository;
import com.hanghae.naegahama.repository.answerrepository.AnswerRepository;
import com.hanghae.naegahama.repository.postrepository.PostQuerydslRepository;
import com.hanghae.naegahama.repository.searcgrepository.SearchQuerydslRepository;
import com.hanghae.naegahama.repository.searcgrepository.SearchRepository;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class SearchService {

    private final AnswerQuerydslRepository answerQuerydslRepository;
    private final PostQuerydslRepository postQuerydslRepository;
    private final SearchQuerydslRepository searchQuerydslRepository;
    private final SearchRepository searchRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    //검색키워드 searchRepository에 저장.
    public SearchPostRequest postSearchList(String searchWord, UserDetailsImpl userDetails) {

        //요청글 검색.
        List<Post> posts = postQuerydslRepository.findPostBySearchWord(searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Long answerCount = answerQuerydslRepository.countBySearchWord(searchWord);

        if (posts.size() == 0) {
            searchWordEvent(searchWord, userDetails);
            return new SearchPostRequest(searchRequests, answerCount);
        }
        for (Post post : posts) {
            String file = null;
            if (post.getFileList().size() != 0) {
                file = post.getFileList().get(0).getUrl();
            }

            SearchRequest searchRequest = new SearchRequest(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getModifiedAt(),
                    file,
                    post.getCategory()
            );
            searchRequests.add(searchRequest);
        }

        SearchPostRequest searchPostRequest = new SearchPostRequest(
                searchRequests,
                answerCount
        );
        searchWordEvent(searchWord, userDetails);

        return searchPostRequest;
    }


    //답변글 검색
    public SearchAnswerRequest answerSearchList(String searchWord, UserDetailsImpl userDetails) {
        log.info("searchWord = {}", searchWord);
        List<Answer> answers = answerQuerydslRepository.findAnswerBySearchWord(searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Long postCount = postQuerydslRepository.countBySearchWord(searchWord);
        if (answers.size() == 0) {
            SearchAnswerRequest searchAnswerRequest = new SearchAnswerRequest(
                    searchRequests,
                    postCount
            );
            searchWordEvent(searchWord, userDetails);
            return searchAnswerRequest;
        }
        /*for (Answer answer : answers) {
            String file = null;
            *//*if (answer.getFileList().size() != 0) {
                file = answer.getFileList().get(0).getUrl();
            }
            SearchRequest searchRequest = new SearchRequest(
                    answer.getId(),
                    answer.getTitle(),
                    answer.getContent(),
                    answer.getModifiedAt(),
                    file,
                    answer.getPost().getCategory()
            );*//*

            searchRequests.add(searchRequest);
        }*/
        try {
            searchRequests = answers.stream()
                    .map(SearchRequest::new)
                    .collect(toList());
        }catch (Exception e){
            searchRequests = answers.stream()
                    .map(a -> new SearchRequest(a,null))
                    .collect(toList());
        }
            SearchAnswerRequest searchAnswerRequest = new SearchAnswerRequest(
                    searchRequests,
                    postCount
            );
            searchWordEvent(searchWord, userDetails);
            return searchAnswerRequest;



    }

    private void searchWordEvent(String searchWord, UserDetailsImpl userDetails) {
        if (!(userDetails == null)) {
            User user = userDetails.getUser();
            if (!searchQuerydslRepository.existSearchByUserWord(user.getId(),searchWord)) {
                Search search = new Search(searchWord, user);
                searchRepository.save(search);
            }
            applicationEventPublisher.publishEvent(new SearchWordEvent(user));
        }
    }

    //최근검색어 조회.
    @Transactional(readOnly = true)
    public List<SearchWords> SearchList(UserDetailsImpl userDetails) {
        return searchQuerydslRepository.findSearchByUser(userDetails.getUser().getId()).stream()
                .map(SearchWords::new)
                .collect(toList());
    }

    //검색어 삭제
    public BasicResponseDto deleteSearch(Long searchId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        searchRepository.deleteByIdAndUser(searchId, user);
        return new BasicResponseDto("success");
    }

    //검색어 전체삭제
    public BasicResponseDto deleteAllSearch(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        searchRepository.deleteByUser(user);
        return new BasicResponseDto("success");
    }
}