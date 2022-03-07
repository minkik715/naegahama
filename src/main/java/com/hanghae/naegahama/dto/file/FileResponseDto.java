package com.hanghae.naegahama.dto.file;

import com.hanghae.naegahama.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class FileResponseDto
{
    private List<String> file;
    private String video;

    public FileResponseDto(List<String> file, String video )
    {
        this.file = file;
        this.video = video;

    }
}
