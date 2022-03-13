package com.hanghae.validtest;

import com.hanghae.naegahama.dto.post.PostRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ValidTest {

    private Class<String> obj;

    @Test
    @DisplayName("게시글 DTO 테스트")
    void validPostDto(){
        PostRequestDto postRequestDto = new PostRequestDto(null,null,null,null,null,1L);
        System.out.println(postRequestDto);
        Assertions.assertThat(postRequestDto.getTimeSet()).isEqualTo(1L);

    }

}
