package com.casemodul4.model;

import com.casemodul4.model.dto.CommentDto;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private UserAcc userAcc;
    @ManyToOne
    private Post post;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Date createDate;
    public CommentDto commentDto() {
        return new CommentDto(id, content, createDate, userAcc.getId(), userAcc.getUsername(), userAcc.getAvatar(), post.getId());
    }

}
