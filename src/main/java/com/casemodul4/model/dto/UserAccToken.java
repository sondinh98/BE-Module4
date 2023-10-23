package com.casemodul4.model.dto;

import com.casemodul4.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccToken {
    private int id;
    private String username;
    private String email;
    private String fullName;
    private String avatar;
    private String coverPhoto;
    private String Description;
    private Role role;
    private String token;


}
