package com.hanghae.naegahama.dto.file;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImageUrlResponseDto
{
    private List<String> imageUrl;

    public ImageUrlResponseDto(List<String> imageUrl )
    {
        this.imageUrl = imageUrl;
    }
}
