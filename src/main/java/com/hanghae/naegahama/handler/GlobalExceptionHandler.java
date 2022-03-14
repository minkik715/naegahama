package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.handler.ex.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AnswerFileNotFoundException.class)
    public ResponseEntity<ErrorResponse> AnswerFileNotFoundException(AnswerFileNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> UserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordCheckFailException.class)
    public ResponseEntity<ErrorResponse> passwordCheckFailException(PasswordCheckFailException e){
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    /*@ExceptionHandler({
            MethodArgumentNotValidException.class
            })
    public ResponseEntity<ErrorResponse> validException(
            MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse("400", "유효성 검사 실패 : " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST); // 2

    }*/

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> EmailNotFoundException(EmailNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordContainsEmailException.class)
    public ResponseEntity<ErrorResponse> handlePasswordContainsEmailException(PasswordContainsEmailException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicationUserNameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserNameException(DuplicationUserNameException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotCollectException.class)
    public ResponseEntity<ErrorResponse> handlePasswordNotCollectException(PasswordNotCollectException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoginUserNotFoundException(LoginUserNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalPostUpdateUserException.class)
    public ResponseEntity<ErrorResponse> handleIllegalPostUpdateUserException(IllegalPostUpdateUserException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalPostDeleteUserException.class)
    public ResponseEntity<ErrorResponse> handleIllegalPostDeleteUserException(IllegalPostDeleteUserException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalCommentUpdateUserException.class)
    public ResponseEntity<ErrorResponse> handleIllegalCommentUpdateUserException(IllegalCommentUpdateUserException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalCommentDeleteUserException.class)
    public ResponseEntity<ErrorResponse> handleIllegalCommentDeleteUserException(IllegalCommentDeleteUserException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<ErrorResponse> AnswerNotFoundException(AnswerNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostWriteTitleNullException.class)
    public ResponseEntity<ErrorResponse> PostWriteTitleNullException(PostWriteTitleNullException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostWriteContentNullException.class)
    public ResponseEntity<ErrorResponse> PostWriteContentNullException(PostWriteContentNullException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostWriteCategoryNullException.class)
    public ResponseEntity<ErrorResponse> PostWriteCategoryNullException(PostWriteCategoryNullException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostWriteLevelNullException.class)
    public ResponseEntity<ErrorResponse> PostWriteLevelNullException(PostWriteLevelNullException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AnswerWritePostClosedException.class)
    public ResponseEntity<ErrorResponse> AnswerWritePostClosedException(AnswerWritePostClosedException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AnswerWriteTitleNullException.class)
    public ResponseEntity<ErrorResponse> AnswerWriteTitleNullException(AnswerWriteTitleNullException e) {
        return new ResponseEntity<>(new ErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                , HttpStatus.BAD_REQUEST);
    }



}