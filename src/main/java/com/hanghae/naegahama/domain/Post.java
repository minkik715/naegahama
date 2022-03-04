package com.hanghae.naegahama.domain;

import com.hanghae.naegahama.dto.post.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    public Post(String title, String content, String category, String level, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.level = level;
        this.user = user;
    }

    @Length(max = 10000)
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String level;

    @JoinColumn(name="room_id")
    @OneToOne
    private Room room;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Answer> answerList;

    @OneToMany(mappedBy = "post")
    private List<PostFile> fileList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user) {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
        this.level = postRequestDto.getLevel();
    }

    public void UpdatePost(PostRequestDto postRequestDto, User user) {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
        this.level = postRequestDto.getLevel();

    }

//    public Category(CategoryResponseDto categoryResponseDto) {
//        this.category = categoryResponseDto.getCategory();
//    }
}