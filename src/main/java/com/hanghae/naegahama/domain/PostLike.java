package com.hanghae.naegahama.domain;

import com.hanghae.naegahama.dto.postlike.PostLikeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "post_like")
@Entity
@Getter
@NoArgsConstructor
public class PostLike extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id", nullable = false)
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public PostLike(PostLikeRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.post = requestDto.getPost();
    }
}
