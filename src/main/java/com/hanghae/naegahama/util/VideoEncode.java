
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

@Slf4j
@Component
public class VideoEncode {

    private FFmpeg ffmpeg;
    private FFprobe ffprobe;
    @PostConstruct
    public void init(){
        try{
            ffmpeg = new FFmpeg("src/main/resources/src/bin/ffmpeg.exe");
            ffprobe = new FFprobe("src/main/resources/src/bin/ffprobe.exe");
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
                .setVideoResolution(640,480)
                //.setAudioChannels(2)
                //.setVideoBitRate(1464800)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg,ffprobe);
        executor.createJob(builder).run();
    }
}

