package com.hanghae.naegahama.dto.post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    public String content;
    @NotBlank(message = "카테고리는 필수입니다.")
    public String category;
    @NotBlank(message = "레벨은 필수입니다.")
    private String level;
    private List<String> file;
    private Long timeSet;

    public void setContent(String content) {
        this.content = content;
    }
}