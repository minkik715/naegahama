package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.login.LoginRequestDto;
import com.hanghae.naegahama.dto.search.SearchAnswerRequestDto;
import com.hanghae.naegahama.dto.search.SearchPostRequestDto;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
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
    public List<SearchPostRequestDto> postSearchList(String searchWord, UserDetailsImpl userDetails) {

        if (userDetails == null) {
        } else {
            User user = userDetails.getUser();
            Search search = new Search(searchWord, user);
            searchRepository.save(search);
        }
        //요청글 검색.
        List<Post> posts = postRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord);

        List<SearchPostRequestDto> response = new ArrayList<>();

        if (posts == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
        for (Post post : posts) {
            Long postCount = postRepository.countByPost(post);
            SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getFileList().get(0).getUrl(),
                    post.getModifiedAt(),
                    postCount
            );
            response.add(searchPostRequestDto);
        }
        return response;
    }


    //답변글 검색
    public List<SearchAnswerRequestDto> answerSearchList(String searchWord) {

        List<Answer> answers = answerRepository.findAllByTitleContainingOrContentContainingOrderByCreatedAtDesc(searchWord);

        List<SearchAnswerRequestDto> response = new ArrayList<>();

        if (answers == null) {
            throw new PostNotFoundException("글이 존재하지 않습니다");
        }
        for (Answer answer : answers) {
            Long answerCount = answerRepository.countByPost(answer);
            SearchAnswerRequestDto searchAnswerRequestDto = new SearchAnswerRequestDto(
                    answer.getId(),
                    answer.getTitle(),
                    answer.getContent(),
                    answer.getFileList().get(0).getUrl(),
                    answer.getModifiedAt(),
                    answerCount
            );
            response.add(searchAnswerRequestDto);
        }
        return response;
    }
}

//    //최근검색어 순위
//    public List<SearchAnswerRequestDto> SearchList() {
//        searchRepository.여기에서 가장 많이 검색된 순위 5개? 정도 꺼내서 보여주기.
//
//}


