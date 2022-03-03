package com.hanghae.naegahama.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Hippoinfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hippoinfo_id", nullable = false)
    private Long id;

    @Column
    private String hippoName;

    @Column
    private String hippoImage;

    @Column
    private Long hippolv;

    @OneToOne ( mappedBy = "hippoinfo")
    @JoinColumn(name = "user_id")
    User user;



}
