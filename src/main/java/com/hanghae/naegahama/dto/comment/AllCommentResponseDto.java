package com.hanghae.naegahama.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AllCommentResponseDto {
    private CommentResponseDto parentComment;
    private List<KidsCommentListResponseDto> childComments = new ArrayList<>();


    public AllCommentResponseDto(CommentResponseDto parentComment, List<KidsCommentListResponseDto> childComments) {
        this.parentComment = parentComment;
        this.childComments = childComments;
    }
}
