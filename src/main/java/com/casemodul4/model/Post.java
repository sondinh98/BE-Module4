package com.casemodul4.model;

import com.casemodul4.model.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private UserAcc userAcc;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Lob // Large Object
    @Column(name = "img", columnDefinition = "LONGBLOB")
    private byte[] img;
    private String video;
    private Date createDate = Date.valueOf(LocalDate.now());
    private int likeCount =0;
    private int commentCount =0;
    private int shareCount =0;
    public PostDTO postDTO() {
        return new PostDTO(id,content,img,video, userAcc.getId());
    }

}
