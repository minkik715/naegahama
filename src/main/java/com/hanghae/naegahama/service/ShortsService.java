package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerVideo;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.shorts.ShortsResponseDto;
import com.hanghae.naegahama.ex.AnswerFileNotFoundException;
import com.hanghae.naegahama.repository.answervideorepository.AnswerVideoQuerydslRepository;
import com.hanghae.naegahama.repository.answervideorepository.AnswerVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ShortsService {

    private final AnswerVideoRepository answerVideoRepository;
    private final AnswerVideoQuerydslRepository answerVideoQuerydslRepository;
    @Transactional(readOnly = true)
    public List<ShortsResponseDto> getOneShorts() {
        List<AnswerVideo> mp4File = answerVideoQuerydslRepository.findVideosByExt("mp4", "short");
        int size = mp4File.size();
        if(size == 0){
            throw new AnswerFileNotFoundException("동영상이 존재하지 않습니다.");
        }
        List<ShortsResponseDto> shortsResponseDtoList = new ArrayList<>();
        for (AnswerVideo answerVideo : mp4File) {
            Answer answer = answerVideo.getAnswer();
            Long postId = answer.getPost().getId();
            User user = answer.getUser();
            String url = answerVideo.getUrl();

            int commentCnt = answer.getCommentList().size();
            if(url.substring(url.lastIndexOf(".")+1).equals("mp4")){
                url = url.replace("mp4", "short");
            }


            shortsResponseDtoList.add(new ShortsResponseDto(url,answer.getTitle(), user.getNickName(), user.getHippoName(), answer.getId(),postId,user.getHippoImage(),commentCnt, user.getId()));



        }

        return shortsResponseDtoList;

    }
}
