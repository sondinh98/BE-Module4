package com.casemodul4.model;

import com.casemodul4.model.dto.UserAccDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserAcc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String email;
    private String fullName;
    private String avatar;
    private String coverPhoto;
    private String Description;
    @ManyToOne
    private Role role;
    public UserAccDTO userAccDTO(){
        return new UserAccDTO(id,username,email,fullName,avatar,coverPhoto,getDescription(),role);
    };

}
