package com.hanghae.naegahama.domain;


import com.hanghae.naegahama.dto.answer.StarPostRequestDto;
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
    private Integer star;

    @Lob
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public Answer(String title, int star, String content, Post post, User user) {
        this.title = title;
        this.star = star;
        this.content = content;
        this.post = post;
        this.user = user;
    }

    @OneToMany(mappedBy = "answer")
    private List<AnswerLike> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "answer")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "answer")
    private List<AnswerFile> fileList = new ArrayList<>();


    public Answer(AnswerPostRequestDto answerPostRequestDto, Post post, User user) {
        this.title = answerPostRequestDto.getTitle();
        this.content = answerPostRequestDto.getContent();
        this.post = post;
        this.user = user;
        this.star = 0;
    }


    public void Update(AnswerPostRequestDto answerPostRequestDto,List<AnswerFile>  fileList){
        this.title = answerPostRequestDto.getTitle();
        this.content = answerPostRequestDto.getContent();
        this.fileList = fileList;
    }

    public Answer(String title, String content, Post post, User user) {
        this.title = title;
        this.content = content;
        this.post = post;
        this.user = user;
    }



    public void Star(StarPostRequestDto starPostRequestDto)
    {
        this.star = starPostRequestDto.getStar();
    }


}
