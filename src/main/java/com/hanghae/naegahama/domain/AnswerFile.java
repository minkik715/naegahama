package com.hanghae.naegahama.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AnswerFile extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Length(max=1000)
    @Column(nullable = false)
    private String url;

    public AnswerFile(String url, Answer answer) {
        this.url = url;
        this.answer = answer;
    }

    @JoinColumn(name = "answer_id")
    @ManyToOne
    private Answer answer;

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public AnswerFile(String url)
    {
        this.url = url;
    }
}
