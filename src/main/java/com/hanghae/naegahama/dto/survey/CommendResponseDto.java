package com.hanghae.naegahama.dto.survey;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommendResponseDto {

    private Long postid;
    private String title;
    private Long postid1;
    private String title1;

    public CommendResponseDto(Long postid, String title, Long postid1, String title1) {
        this.postid = postid;
        this.title = title;
        this.postid1 = postid1;
        this.title1 = title1;

    }


}
