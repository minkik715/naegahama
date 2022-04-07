package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.ex.*;
import io.sentry.Sentry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ErrorResponse> TokenInvalidException(TokenInvalidException e){
        Sentry.captureException(e);
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AnswerFileNotFoundException.class)
    public ResponseEntity<ErrorResponse> AnswerFileNotFoundException(AnswerFileNotFoundException e){
        Sentry.captureException(e);
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<ErrorResponse> LoginFailException(LoginFailException e){
        Sentry.captureException(e);
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> UserNotFoundException(UserNotFoundException e){
        Sentry.captureException(e);
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<ErrorResponse> AnswerNotFoundException(AnswerNotFoundException e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}