package com.hanghae.naegahama.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Length(max = 10000)
    @Column(name = "content", nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer level;
}
