package com.hanghae.naegahama.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private Long parentCommentId;

}
