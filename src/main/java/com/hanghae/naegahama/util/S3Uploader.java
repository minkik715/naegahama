package com.hanghae.naegahama.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final VideoEncode videoEncode;
    @Value("${cloud.aws.s3.bucket}")
    public String naegahama;

    public String upload(MultipartFile multipartFile, String dirName, Boolean isVideo) throws IOException{
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));


        if(isVideo){
            File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
            String uuid = UUID.randomUUID().toString();

            // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                    fos.write(multipartFile.getBytes());
                }
            }
            log.info("ok");
            videoEncode.videoEncode(convertFile.getAbsolutePath(),System.getProperty("user.dir") + "/test" + multipartFile.getOriginalFilename());
            videoEncode.cutVideo(convertFile.getAbsolutePath(), System.getProperty("user.dir") + "/shorts" + multipartFile.getOriginalFilename());
            File file = new File(System.getProperty("user.dir") + "/test" + multipartFile.getOriginalFilename());
            log.info("긴 동영상 로컬 성공");
            log.info("파일저장장소 = {}",file.getAbsolutePath());
            removeNewFile(uploadFile);
            if(videoEncode.getVideoLength(file.getAbsolutePath()) <15){
                return upload(file, dirName,true,uuid);
            }
            File shortFile = new File(System.getProperty("user.dir") + "/shorts" + multipartFile.getOriginalFilename());
            log.info("쇼츠 동영상 로컬 성공");
            log.info("파일저장장소 = {}",shortFile.getAbsolutePath());



            upload(shortFile,dirName,true,uuid);


            return upload(file, dirName,false,uuid);


       }else {
            return upload(uploadFile, dirName,false,UUID.randomUUID().toString());
       }
    }
    // S3로 파일 업로드하기
    public String upload(File uploadFile, String dirName, Boolean isShort, String uuid) {
        if(isShort){
            String fileName = dirName + "/" + uuid +".short";   // S3에 저장된 파일 이름
            return putS3(uploadFile, fileName);
        }
//        String fileName = dirName + "/" + uuid +".mp4";
        String fileName = dirName + "/" +uuid +"."+uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".")+1);   // S3에 저장된 파일 이름
        return putS3(uploadFile, fileName);
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(naegahama, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        removeNewFile(uploadFile);
        return amazonS3Client.getUrl(naegahama, fileName).toString();
    }

    // S3로 삭제
    public void deleteS3( String fileName)
    {

        Boolean isExistObject = amazonS3Client.doesObjectExist(naegahama,fileName);
        if (isExistObject)
        {
            amazonS3Client.deleteObject(naegahama,fileName);




        }
        else{
            System.out.println("삭제할 s3 파일 존재하지 않음");
        }
    }

    public S3Object getObject(String fileName)
    {
        Boolean isExistObject = amazonS3Client.doesObjectExist(naegahama,fileName);
        if (isExistObject)
        {
            return amazonS3Client.getObject(naegahama,fileName);
        }
        else{
            throw new IllegalArgumentException("s3 파일 존재하지 않음");
        }
    }


    // 로컬에 저장된 이미지 지우기
    public void removeNewFile(File targetFile) {
        targetFile.delete();
        log.info("{} delete success", targetFile.getName());
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException
    {
        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        System.out.println(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
        if (convertFile.createNewFile())
        {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
