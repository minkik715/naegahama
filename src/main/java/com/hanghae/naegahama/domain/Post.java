package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PutRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    public Post(String title, String content, String category, String level, User user, List<PostFile> fileList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.level = level;
        this.user = user;
        this.fileList = fileList;

    }
    public Post(String title, String content, String category, String level, User user, String state) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.level = level;
        this.user = user;
        this.state = state;
    }

    @Length(max = 10000)
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String level;

    @Column
    private String state;

    @Column
    private String timeSet;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;


    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<Answer> answerList;

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<PostFile> fileList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user, String state)
    {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
        this.level = postRequestDto.getLevel();
        this.state = state;
    }

    public void UpdatePost(PutRequestDto postRequestDto)
    {
        this.content = postRequestDto.getContent();
    }


//    public Category(CategoryResponseDto categoryResponseDto) {
//        this.category = categoryResponseDto.getCategory();
//    }
}