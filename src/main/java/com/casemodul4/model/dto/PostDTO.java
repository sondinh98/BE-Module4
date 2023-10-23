package com.casemodul4.model.dto;

import com.casemodul4.model.UserAcc;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class PostDTO {
    private int id;
    private String content;
    private byte[] img;
    private String video;
    private int idUser;
}
