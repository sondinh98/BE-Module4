package com.casemodul4.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class GroupPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Group1 group1;

    @ManyToOne
    private UserAcc userAcc;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "TEXT")
    private String image;
    @Column(columnDefinition = "TEXT")
    private String video;

    private Date createdDate;

    private int likeCount;

    private int commentCount;

    private int shareCount;

}
