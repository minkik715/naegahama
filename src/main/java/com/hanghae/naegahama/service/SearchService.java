package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.CommentResponseDto;
import com.hanghae.naegahama.dto.post.PostResponseDto;
import com.hanghae.naegahama.dto.search.SearchAnswerRequest;
import com.hanghae.naegahama.dto.search.SearchPostRequest;
import com.hanghae.naegahama.dto.search.SearchRequest;
import com.hanghae.naegahama.dto.search.SearchWords;
import com.hanghae.naegahama.handler.ex.PostNotFoundException;
import com.hanghae.naegahama.handler.ex.SearchNotFoundException;
import com.hanghae.naegahama.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
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


    //검색키워드 searchRepository에 저장.
    public SearchPostRequest postSearchList(String searchWord, UserDetailsImpl userDetails) {
        log.info("searchWord = {}", searchWord);

        if (!(userDetails == null)) {
            User user = userDetails.getUser();
            if (!searchRepository.existsBySearchWordAndUser(searchWord, user)) {
                Search search = new Search(searchWord, user);
                searchRepository.save(search);
            }
        }

        //요청글 검색.
        List<Post> posts = postRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord, searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Integer answerCount = answerRepository.countByContentContainingOrTitleContaining(searchWord, searchWord);
        if (posts.size() == 0) {

            SearchPostRequest searchPostRequest = new SearchPostRequest(
                    searchRequests,
                    answerCount
            );
            return searchPostRequest;
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
        return searchPostRequest;
    }


    //답변글 검색
    public SearchAnswerRequest answerSearchList(String searchWord) {

        log.info("searchWord = {}", searchWord);
        List<Answer> Answers = answerRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord, searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Integer postCount = postRepository.countByContentContainingOrTitleContaining(searchWord, searchWord);
        if (Answers.size() == 0) {
            SearchAnswerRequest searchAnswerRequest = new SearchAnswerRequest(
                    searchRequests,
                    postCount
            );
            return searchAnswerRequest;
        }
        for (Answer Answer : Answers) {
            String file = null;
            if (Answer.getFileList().size() != 0) {
                file = Answer.getFileList().get(0).getUrl();
            }
            SearchRequest searchRequest = new SearchRequest(
                    Answer.getId(),
                    Answer.getTitle(),
                    Answer.getContent(),
                    Answer.getModifiedAt(),
                    file,
                    Answer.getPost().getCategory()
            );
            searchRequests.add(searchRequest);
        }

        SearchAnswerRequest searchAnswerRequest = new SearchAnswerRequest(
                searchRequests,
                postCount
        );
        return searchAnswerRequest;
    }

    //최근검색어 조회.
    public List<SearchWords> SearchList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Search> searches = searchRepository.findAllByUserOrderByCreatedAtDesc(user);
        List<SearchWords> response = new ArrayList<>();

        if (searches == null) {
            throw new SearchNotFoundException("검색어가 존재하지 않습니다");
        }

        for (Search search : searches) {
            SearchWords searchWords = new SearchWords(search);

            response.add(searchWords);
        }
        return response;
    }

    //검색어 삭제
    public ResponseEntity deleteSearch(Long searchId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        searchRepository.deleteByIdAndUser(searchId, user);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    //검색어 전체삭제
    public ResponseEntity<?> deleteAllSearch(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        searchRepository.deleteByUser(user);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }sdfsdfsdfsdfsdf
}