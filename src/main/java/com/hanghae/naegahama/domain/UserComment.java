package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.dto.userpagecommentdto.UserCommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserComment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private Long parentCommentId;

    @JsonManagedReference
    @JoinColumn(name = "pageuser_id")
    @ManyToOne
    private User pageUser;

    @JsonManagedReference
    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;


    public UserComment(UserCommentRequestDto commentRequestDto, User pageUser, User writer) {
        this.content = commentRequestDto.getContent();
        this.parentCommentId = commentRequestDto.getParentId();
        this.pageUser = pageUser;
        this.writer = writer;
    }

    public void setComment(String content)
    {
        this.content = content;
    }


}
