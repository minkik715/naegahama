package com.hanghae.naegahama.domain;


import lombok.Getter;

import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;

import lombok.NoArgsConstructor;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Answer extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String star;

    @Lob
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "answer")
    private List<AnswerLike> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "answer")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "answer")
    private List<File> fileList = new ArrayList<>();


    public Answer(AnswerPostRequestDto answerPostRequestDto, Post post, User user) {
        this.title = answerPostRequestDto.getTitle();
        this.content = answerPostRequestDto.getContent();
        this.post = post;
        this.user = user;
    }


    public void Update(AnswerPostRequestDto answerPostRequestDto,List<File>  fileList)
    {
        this.title = answerPostRequestDto.getTitle();
        this.content = answerPostRequestDto.getContent();
        this.fileList = fileList;
    }


}
