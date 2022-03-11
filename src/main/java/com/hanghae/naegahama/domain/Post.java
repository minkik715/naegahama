package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PutRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends Timestamped implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String level;


    @Column
    private String status;

    @Column
    private LocalDateTime deadLine;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public void setDeadLine(LocalDateTime deadLine)
    {
        this.deadLine = deadLine;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<Answer> answerList;

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<PostFile> fileList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    public Post(String title, String content, String category, String level, User user, List<PostFile> fileList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.level = level;
        this.user = user;
        this.fileList = fileList;
        this.status = "true";
    }

    public Post(String title, String content, String category, String level, User user, int timeSet) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.level = level;
        this.user = user;
        this.status = "true";
        this.deadLine = LocalDateTime.now().plusHours((long)timeSet);
    }

    public Post(PostRequestDto postRequestDto, User user)
    {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
        this.level = postRequestDto.getLevel();
        this.status = "true";
    }

    public void UpdatePost(PutRequestDto postRequestDto)
    {
        this.content = postRequestDto.getContent();
    }

    @Override
    public int compareTo(Post o) {
        return o.getPostLikes().size() - getPostLikes().size();
    }

    public void setStatus(String status) {
        this.status = status;
    }


//    public Category(CategoryResponseDto categoryResponseDto) {
//        this.category = categoryResponseDto.getCategory();
//    }
}