package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.handler.event.SearchWordEvent;
import com.hanghae.naegahama.dto.search.SearchAnswerRequest;
import com.hanghae.naegahama.dto.search.SearchPostRequest;
import com.hanghae.naegahama.dto.search.SearchRequest;
import com.hanghae.naegahama.dto.search.SearchWords;
import com.hanghae.naegahama.ex.SearchNotFoundException;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class SearchService {

    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final SearchRepository searchRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    //검색키워드 searchRepository에 저장.
    public SearchPostRequest postSearchList(String searchWord, UserDetailsImpl userDetails) {
        log.info("searchWord = {}", searchWord);



        //요청글 검색.
        List<Post> posts = postRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord, searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Integer answerCount = answerRepository.countByContentContainingOrTitleContaining(searchWord, searchWord);
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
        List<Answer> Answers = answerRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord, searchWord);
        List<SearchRequest> searchRequests = new ArrayList<>();

        Integer postCount = postRepository.countByContentContainingOrTitleContaining(searchWord, searchWord);
        if (Answers.size() == 0) {
            SearchAnswerRequest searchAnswerRequest = new SearchAnswerRequest(
                    searchRequests,
                    postCount
            );
            searchWordEvent(searchWord, userDetails);
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
        searchWordEvent(searchWord, userDetails);
        return searchAnswerRequest;
    }

    private void searchWordEvent(String searchWord, UserDetailsImpl userDetails) {
        if (!(userDetails == null)) {
            User user = userDetails.getUser();
            if (!searchRepository.existsBySearchWordAndUser(searchWord, user)) {
                Search search = new Search(searchWord, user);
                searchRepository.save(search);
            }
            User achievementUser = userRepository.findById(user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
            applicationEventPublisher.publishEvent(new SearchWordEvent(achievementUser));
        }
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