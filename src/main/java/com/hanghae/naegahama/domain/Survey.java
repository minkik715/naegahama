//package com.hanghae.naegahama.domain;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class Survey extends Timestamped{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "survey_id", nullable = false)
//    private Long id;
//
//    @JoinColumn(name = "survey")
//    @OneToOne
//    private Survey Survey;
//}
