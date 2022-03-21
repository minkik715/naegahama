//package com.hanghae.naegahama.handler;
//
//import com.hanghae.naegahama.alarm.AlarmType;
//import com.hanghae.naegahama.domain.*;
//import com.hanghae.naegahama.dto.event.AlarmEventListener;
//import com.hanghae.naegahama.dto.event.AnswerVideoEncoding;
//import com.hanghae.naegahama.repository.AnswerRepository;
//import com.hanghae.naegahama.repository.AnswerVideoRepository;
//import com.hanghae.naegahama.util.S3Uploader;
//import com.hanghae.naegahama.util.VideoEncode;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import net.bramp.ffmpeg.FFmpeg;
//import net.bramp.ffmpeg.FFprobe;
//import org.apache.commons.io.FileUtils;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//
//import javax.transaction.Transactional;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLDecoder;
//import java.util.UUID;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class AnswerWritingEvent
//{
//    private final S3Uploader s3Uploader;
//    private final AnswerRepository answerRepository;
//    private final AnswerVideoRepository answerVideoRepository;
//    private final VideoEncode videoEncode;
//    private FFmpeg ffmpeg;
//    private FFprobe ffprobe;
//
//    @Transactional(Transactional.TxType.REQUIRES_NEW)
//
//    public void answerLikeListener(AnswerVideoEncoding answerVideoEncoding) throws IOException {
//        Answer answer = answerRepository.findById(answerVideoEncoding.getAnswerID()).orElseThrow(
//                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));
//
//        AnswerVideo video = answerVideoRepository.findByAnswer(answer).orElseThrow(
//                () -> new IllegalArgumentException("해당 영상은 존재하지 않습니다."));
//
//        String fileName = URLDecoder.decode(video.getUrl().split("static/")[1], "UTF-8");
//
//        InputStream in = s3Uploader.getObject("static/" + fileName).getObjectContent();
//
//        File tempFile = File.createTempFile(String.valueOf(in.hashCode()),".mp4");
//        tempFile.deleteOnExit();
//
//        FileUtils.copyInputStreamToFile(in,tempFile);
//
//        String uuid = UUID.randomUUID().toString();
//
//        videoEncode.videoEncode(tempFile.getAbsolutePath(),System.getProperty("user.dir") + "/test" + fileName);
//        videoEncode.cutVideo(tempFile.getAbsolutePath(), System.getProperty("user.dir") + "/shorts" + fileName);
//
//        File file1 = new File(System.getProperty("user.dir") + "/test" + fileName);
//        File file2 = new File(System.getProperty("user.dir") + "/shorts" + fileName);
//
////        s3Uploader.removeNewFile(tempFile);
//        if (videoEncode.getVideoLength(tempFile.getAbsolutePath()) > 15)
//        {
//            s3Uploader.upload(file1, "static", false, uuid);
////             s3Uploader.upload(tempFile, "static", true, uuid);
//        }
//
////        s3Uploader.upload(tempFile, "static", false, uuid);
//        s3Uploader.upload(file2, "static", true, uuid);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//       // s3Uploader.S3upload(tempFile, "static",false,UUID.randomUUID().toString());
//
//        // 기존에 있던 이미지 파일 S3에서 삭제
//       /* for (PostFile deleteS3 : post.getFileList()) {
//            String[] fileKey = deleteS3.getUrl().split("static/");
//            try {
//                String decodeKey = URLDecoder.decode(fileKey[1], "UTF-8");
//                s3Uploader.deleteS3("static/" + decodeKey);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }*/
//
//    }
//}
