package com.hanghae.naegahama.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private Long parentCommentId;

    @JoinColumn(name = "answer_id")
    @ManyToOne
    private Answer answer;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public Comment(String commentContent, Answer findAnswer, User user) {
        this.content = commentContent;
        this.answer = findAnswer;
        this.user = user;
        findAnswer.getCommentList().add(this);
    }

    public Comment(String commentContent,Long parentCommentId, Answer findAnswer, User user) {
        this.content = commentContent;
        this.parentCommentId = parentCommentId;
        this.answer = findAnswer;
        this.user = user;
        findAnswer.getCommentList().add(this);
    }

    public void setContent(String content) {
        this.content = content;
    }
}
