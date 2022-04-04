package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    @JsonManagedReference
    @JoinColumn(name="parent_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @JsonBackReference
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childCommentList = new ArrayList<>();

    @JsonManagedReference
    @JoinColumn(name = "answer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String timestamp;

    //일반 댓글 생성
    public Comment(String commentContent, Answer findAnswer, User user,String timestamp) {
        this.content = commentContent;
        this.answer = findAnswer;
        this.user = user;
        this.timestamp = timestamp;
        this.parentComment = null;
        findAnswer.getCommentList().add(this);
    }

    //대댓글 생성
    public Comment(String commentContent,Comment parentComment, Answer findAnswer, User user) {
        this.content = commentContent;
        this.parentComment = parentComment;
        this.answer = findAnswer;
        this.user = user;
        this.timestamp = null;
        findAnswer.getCommentList().add(this);
    }

    public void setComment(String content, String timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }
}
