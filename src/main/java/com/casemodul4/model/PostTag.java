package com.casemodul4.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Tag tag;

}