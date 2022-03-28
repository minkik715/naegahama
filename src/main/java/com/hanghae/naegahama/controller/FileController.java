package com.hanghae.naegahama.controller;
import com.hanghae.naegahama.dto.file.FileResponseDto;
import com.hanghae.naegahama.dto.file.ImageUrlResponseDto;
import com.hanghae.naegahama.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class FileController
{
    private final FileService fileService;

    @PostMapping("/api/upload")
    public FileResponseDto fileURL(@RequestPart(name = "file", required = false) List<MultipartFile> multipartFileList,
                                                   @RequestPart(name = "video",required = false) MultipartFile videoFile) throws IOException {
        return fileService.fileURL(multipartFileList, videoFile);
    }


    // 요청글 전체조회
    @GetMapping("/api/image/{type}/{id}")
    public ImageUrlResponseDto imgUrlList(@PathVariable String type , @PathVariable Long id)
    {
        return fileService.imgUrlList(type, id);
    }

}
