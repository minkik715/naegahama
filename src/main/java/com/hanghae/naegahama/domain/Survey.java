//package com.hanghae.naegahama.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//public class Survey extends Timestamped {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "survey_id", nullable = false)
//    private Long id;
//
//    @Column(nullable = false)
//    private String hippoName;
//
//    @JoinColumn(name = "survey")
//    @OneToOne
//    private User user;
//
//    public Survey(String hippo, User user) {
//        this.hippo = hippo;
//        this.user = user;
//
//    }
//}
