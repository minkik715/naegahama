package com.hanghae.naegahama.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class File extends Timestamped{

    @Id
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String url;

    @JoinColumn(name = "answer_id")
    @ManyToOne
    private Answer answer;

    public File(String url)
    {
        this.url = url;
    }
}
