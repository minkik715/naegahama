package com.hanghae.naegahama.dto.comment;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.Comment;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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
