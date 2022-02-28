package com.hanghae.naegahama.domain;

import javax.persistence.*;

@Entity
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

}
