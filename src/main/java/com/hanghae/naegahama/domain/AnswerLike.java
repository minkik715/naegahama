package com.hanghae.naegahama.domain;

import com.hanghae.naegahama.dto.answerlike.AnswerLikeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "answer_like")
@Entity
@Getter
@NoArgsConstructor
public class AnswerLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id", nullable = false)
    private Long id;

    @JoinColumn(name = "answer_id")
    @ManyToOne
    private Answer answer;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public AnswerLike(AnswerLikeRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.answer = requestDto.getAnswer();
    }
}
