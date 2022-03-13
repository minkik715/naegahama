package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerFile;
import com.hanghae.naegahama.domain.AnswerVideo;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.shorts.ShortsResponseDto;
import com.hanghae.naegahama.handler.ex.AnswerFileNotFoundException;
import com.hanghae.naegahama.repository.AnswerFileRepository;
import com.hanghae.naegahama.repository.AnswerVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ShortsService {

    private final AnswerVideoRepository answerVideoRepository;

    public ResponseEntity<?> getOneShorts() {
        List<AnswerVideo> mp4File = answerVideoRepository.findAllByUrlEndingWithOrUrlEndingWithOrderByCreatedAtDesc("mp4", "short");

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
            if(url.substring(url.lastIndexOf(".")+1).equals("mp4")){
                url = url.replace("mp4", "short");
            }
            shortsResponseDtoList.add(new ShortsResponseDto(url,answer.getTitle(), user.getNickName(), user.getHippoName(), answer.getId(),postId));
        }

        return ResponseEntity.ok().body(shortsResponseDtoList);

    }
}
