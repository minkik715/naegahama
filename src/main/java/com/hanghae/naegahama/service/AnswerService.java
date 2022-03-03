package com.hanghae.naegahama.service;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerDetailGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerGetResponseDto;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import com.hanghae.naegahama.dto.answer.StarPostRequestDto;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.util.S3Uploader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AnswerService
{
    private final AnswerRepository answerRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final FileRepository fileRepository;

    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<?> answerWrite(AnswerPostRequestDto answerPostRequestDto, List<MultipartFile> multipartFile, Long postId, UserDetailsImpl userDetails)
            throws IOException
    {
//        String Url = s3Uploader.upload(multipartFile, "static");
        //유저를 받고
        User user = userDetails.getUser();

        //answer와 연결된 post를 찾고
        Post post = postRepository.findPostById(postId);

        //filelist가 빈 Answer를 미리 하나 만들어두고
        Answer answer = new Answer(answerPostRequestDto,post,user);

        //저장된 Answer을 꺼내와서
        Answer saveAnwser = answerRepository.save(answer);
        log.info("saveAnswer Id = {}", saveAnwser.getId());
        //들어온 파일들을 하나씩 처리하는데
        for ( MultipartFile file : multipartFile)
        {
            String url = s3Uploader.upload(file, "static");
            //S3에서 받아온 URL을 통해서 file을 만들고
            File fileUrl = new File(url);

            //파일에 아까 저장한 Answer를 Set한 후에
            fileUrl.setAnswer(saveAnwser);
            //저장된 파일을 Answer에 넣어준다
            File saveFile = fileRepository.save(fileUrl);
            saveAnwser.getFileList().add(saveFile);
        }

        return ResponseEntity.ok().body(new BasicResponseDto("true"));

    }







    public List<String> fileTest(List<MultipartFile> multipartFile) throws IOException
    {
        List<String> urlList = new ArrayList<>();

        for ( MultipartFile file : multipartFile)
        {

            String url = s3Uploader.upload(file, "static");
            urlList.add(url);
            log.info(url);
        }

         return urlList;
    }

    public List<AnswerGetResponseDto> answerList(Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<Answer> answerList = answerRepository.findAllByPostIdOrderByCreatedAt(postId);
        List<AnswerGetResponseDto> answerGetResponseDtoList = new ArrayList<>();

        for ( Answer answer : answerList)
        {
            Long commentCount = commentRepository.countByAnswer(answer);
            Long likeCount = answerLikeRepository.countByAnswer(answer);

            AnswerGetResponseDto answerGetResponseDto = new AnswerGetResponseDto(answer,commentCount,likeCount);
            answerGetResponseDtoList.add(answerGetResponseDto);
        }

        return answerGetResponseDtoList;

    }


    public ResponseEntity<?> answerUpdate(Long answerId, UserDetailsImpl userDetails, AnswerPostRequestDto answerPostRequestDto, List<MultipartFile> multipartFile) throws IOException
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        List<File> fileList = new ArrayList<>();

        for ( MultipartFile file : multipartFile)
        {
            String url = s3Uploader.upload(file, "static");
            File fileUrl = new File(url);
            fileList.add(fileUrl);
        }

        answer.Update(answerPostRequestDto,fileList);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> answerDelete(Long answerId, UserDetailsImpl userDetails)
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        answerLikeRepository.deleteByAnswer(answer);
        fileRepository.deleteByAnswer(answer);
        commentRepository.deleteByAnswer(answer);

        answerRepository.deleteById(answerId);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    public AnswerDetailGetResponseDto answerDetail(Long answerId, UserDetailsImpl userDetails)
    {
        Answer answer =  answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        Long likeCount = answerLikeRepository.countByAnswer(answer);
        Long commentCount = commentRepository.countByAnswer(answer);

        List<AnswerLike> likeList = answer.getLikeList();
        List<Long> likeUserList = new ArrayList<>();

        for ( AnswerLike likeUser : likeList)
        {
            likeUserList.add(likeUser.getUser().getId());
        }

        AnswerDetailGetResponseDto answerDetailGetResponseDto = new AnswerDetailGetResponseDto(answer,likeCount,commentCount,likeUserList);

        return answerDetailGetResponseDto;
    }


    public ResponseEntity<?> answerStar(Long answerId, UserDetailsImpl userDetails, StarPostRequestDto starPostRequestDto)
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        if( answer.getStar() != null )
        {
            throw new IllegalArgumentException("이미 평가한 답글입니다.");
        }

        answer.Star(starPostRequestDto);
        User answerWriter = answer.getUser();

        answerWriter.addPoint(starPostRequestDto.getStar());



        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


}
