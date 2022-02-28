package com.hanghae.naegahama.domain;

import javax.persistence.*;

@Entity
public class File extends Timestamped{

    @Id
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String url;

    @JoinColumn(name = "answer_id")
    @ManyToOne
    private Answer answer;
}
