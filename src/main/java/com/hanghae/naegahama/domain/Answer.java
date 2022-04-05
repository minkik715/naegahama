package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import com.hanghae.naegahama.dto.answer.AnswerPutRequestDto;
import com.hanghae.naegahama.dto.answer.StarPostRequestDto;
import lombok.Getter;
<<<<<<< HEAD

import lombok.NoArgsConstructor;


=======
import lombok.NoArgsConstructor;

>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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



    @JsonManagedReference
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch =FetchType.LAZY)
    private User user;

    public Answer(String title, int star, String content, Post post, User user) {
        this.title = title;
        this.star = star;
        this.content = content;
        this.post = post;
        this.user = user;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<AnswerLike> likeList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "answer",cascade = CascadeType.REMOVE)
    private List<AnswerFile> fileList = new ArrayList<>();


    public Answer(AnswerPostRequestDto answerPostRequestDto, Post post, User user)
    {
        this.title = answerPostRequestDto.getTitle();
        this.content = answerPostRequestDto.getContent();
        this.post = post;
        this.user = user;
        this.star = 0;
    }



    public void Update(AnswerPutRequestDto answerPutRequestDto)
    {
        this.title = answerPutRequestDto.getTitle();
        this.content = answerPutRequestDto.getContent();

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
