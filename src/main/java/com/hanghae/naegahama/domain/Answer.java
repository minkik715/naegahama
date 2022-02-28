package com.hanghae.naegahama.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Answer extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "answer_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

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
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "answer")
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "answer")
    private List<File> fileList = new ArrayList<>();



}
