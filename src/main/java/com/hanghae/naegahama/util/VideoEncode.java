
package com.hanghae.naegahama.util;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class VideoEncode {

    private FFmpeg ffmpeg;
    private FFprobe ffprobe;
    @PostConstruct
    public void init(){
        try{
            //locat에서 돌릴떄
           // ffmpeg = new FFmpeg("src/main/resources/src/bin/ffmpeg.exe");
            //ffprobe = new FFprobe("src/main/resources/src/bin/ffprobe.exe");
            //ec2에 올릴떄
           ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
            ffprobe = new FFprobe("/usr/bin/ffprobe");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void videoEncode(String fileUrl,String savePath) throws IOException {
        FFmpegProbeResult probeResult = ffprobe.probe(fileUrl);


        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(fileUrl)
                .overrideOutputFiles(true)
                .addOutput(savePath)
                .setFormat("mp4")
                //.setVideoCodec("libx264")
                //.setVideoPixelFormat("yuv420p")
                .setVideoResolution(426,240)
                //.setAudioChannels(2)
                //.setVideoBitRate(1464800)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg,ffprobe);
        executor.createJob(builder).run();
    }

    public void cutVideo(String fileUrl,String savePath) throws IOException {
        FFmpegProbeResult probeResult = ffprobe.probe(fileUrl);

        log.info("동영상의 길이 = {}", probeResult.streams.get(0).duration);
        if(probeResult.streams.get(0).duration > 15){
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(fileUrl)
                    .overrideOutputFiles(true)
                    .addOutput(savePath)
                    .setFormat("mp4")
                    //.setVideoCodec("libx264")
                    //.setVideoPixelFormat("yuv420p")
                    .setVideoResolution(426,240)
                    .setDuration(15, TimeUnit.SECONDS)
                    //.setAudioChannels(2)
                    //.setVideoBitRate(1464800)
                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .done();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg,ffprobe);
            executor.createJob(builder).run();
        }

    }


    public int getVideoLength(String fileUrl ) throws IOException {
        FFmpegProbeResult probeResult = ffprobe.probe(fileUrl);

        return (int) probeResult.streams.get(0).duration;
    }


}

