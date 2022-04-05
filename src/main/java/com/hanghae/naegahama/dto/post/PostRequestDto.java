package com.hanghae.naegahama.dto.post;


<<<<<<< HEAD
import com.sun.istack.NotNull;
import lombok.*;
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

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