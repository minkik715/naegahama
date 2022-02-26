package com.hanghae.naegahama.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class File {

    @Id
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String url;
}
