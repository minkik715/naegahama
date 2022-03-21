package com.hanghae.naegahama.handler.event;

import com.hanghae.naegahama.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PostWriteEvent {
    private Post post;
}
