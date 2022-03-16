package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostFile extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String url;

    @JsonManagedReference
    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    public PostFile(String url)
    {
        this.url = url;
    }

    public PostFile(String url, Post post) {
        this.url = url;
        this.post = post;
    }
}

