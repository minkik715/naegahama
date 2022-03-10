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
 /*   public ResponseEntity<?> getThreeShorts() {
        List<AnswerFile> mp4File = answerFileRepository.findAllByUrlEndingWith("mp4");
        long size = (long)mp4File.size();
        HashSet<Long> randomSet = new HashSet<>();
        while(randomSet.size() != 3){
            randomSet.add((long) (Math.random() * size));
        }
        ArrayList<Long> arrayList = new ArrayList<>(randomSet);

        ArrayList<ShortsResponseDto> shortsResponseListDto = new ArrayList<>();

        for (Long num : arrayList) {
            AnswerFile findAnswerFile = answerFileRepository.findById(num).orElseThrow(
                    () -> new AnswerNotFoundException("존재하지않는 답변글입니다.")
            );
            Answer answer = findAnswerFile.getAnswer();
            User user = answer.getUser();
            shortsResponseListDto.add(new ShortsResponseDto(findAnswerFile.getUrl(),answer.getTitle(), user.getNickName(), user.getHippoName(), answer.getId()));
        }
        return ResponseEntity.ok().body(shortsResponseListDto);
    }*/

    public ResponseEntity<?> getOneShorts() {
        List<AnswerVideo> mp4File = answerVideoRepository.findAllByStatusAndUrlEndingWithOrUrlEndingWith(true,"mp4", "short");

        int size = mp4File.size();
        if(size == 0){
            throw new AnswerFileNotFoundException("동영상이 존재하지 않습니다.");
        }
        int randomNum = ((int) (Math.random() * size));

            AnswerVideo findAnswerFile = mp4File.get(randomNum);
            findAnswerFile.setStatus(false);
            Answer answer = findAnswerFile.getAnswer();
            User user = answer.getUser();
            String url = findAnswerFile.getUrl();
            if(url.substring(url.lastIndexOf(".")+1).equals("mp4")){
                url = url.replace("mp4", "short");
            }
        ShortsResponseDto shortsResponseDto =(new ShortsResponseDto(url,answer.getTitle(), user.getNickName(), user.getHippoName(), answer.getId()));
        return ResponseEntity.ok().body(shortsResponseDto);

    }
}
