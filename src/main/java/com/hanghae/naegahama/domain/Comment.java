package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @JoinColumn(name = "answer_id")
    @ManyToOne
    private Answer answer;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    //일반 댓글 생성
    public Comment(String commentContent, Answer findAnswer, User user) {
        this.content = commentContent;
        this.answer = findAnswer;
        this.user = user;
        this.parentCommentId = null;
        findAnswer.getCommentList().add(this);
    }

    //대댓글 생성
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
