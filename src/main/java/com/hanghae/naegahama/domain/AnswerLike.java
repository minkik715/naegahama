package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @JoinColumn(name = "answer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public AnswerLike(AnswerLikeRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.answer = requestDto.getAnswer();
    }
}
