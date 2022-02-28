package com.hanghae.naegahama.domain;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "answer")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "answer")
    private List<Like> likeList;

    @OneToMany(mappedBy = "answer")
    private List<File> fileList;

}
