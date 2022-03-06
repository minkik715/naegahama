package com.hanghae.naegahama.controller;


import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.file.FileResponseDto;

import com.hanghae.naegahama.service.FileService;
import com.hanghae.naegahama.util.S3Uploader;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileController
{
    private final FileService fileService;


    @PostMapping("/api/upload")
    public ResponseEntity<?> fileURL(@RequestPart(name = "file", required = false) List<MultipartFile> multipartFileList,
                                     @RequestPart(name = "video",required = false) MultipartFile videoFile) throws IOException
    {
       return fileService.fileURL(multipartFileList,videoFile);



    }


}
