package com.hanghae.naegahama.domain;

import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Answer extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "answer")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "answer")
    private List<Like> likeList;

    @OneToMany(mappedBy = "answer")
    private List<File> fileList;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public Answer(AnswerPostRequestDto answerPostRequestDto, Post post, List<File> fileList, User user)
    {
        this.title = answerPostRequestDto.getTitle();
        this.content = answerPostRequestDto.getContent();
        this.post = post;
        this.user = user;
        this.fileList = fileList;
    }


}
