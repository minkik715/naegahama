package com.hanghae.naegahama.dto.userpagecommentdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public class UserPageCommentListResponseDto {

    private Long userId;

    private List<UserCommentResponseDto> comments = new ArrayList<>();



}
