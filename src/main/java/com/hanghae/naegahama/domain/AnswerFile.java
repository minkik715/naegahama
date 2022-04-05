package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class AnswerFile extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;


    @Column(nullable = false, length = 1000)
    private String url;

    public AnswerFile(String url, Answer answer) {
        this.url = url;
        this.answer = answer;
        answer.getFileList().add(this);
    }

    @JsonManagedReference
    @JoinColumn(name = "answer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;



    public void setAnswer(Answer answer) {
        this.answer = answer;
    }


}
