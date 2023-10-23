package com.casemodul4.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
@AllArgsConstructor
@Data
public class CommentDto {
    private int id;
    private String content;
    private Date createDate;
    private int userAccId;
    private String username;
    private String avatar;
    private int postId;

//    public CommentDto(int id, String content, Date createDate, int userAccId, String username, String avatar, int postId) {
//        this.id = id;
//        this.content = content;
//        this.createDate = createDate;
//        this.userAccId = userAccId;
//        this.username = username;
//        this.avatar = avatar;
//        this.postId = postId;
//    }
}
