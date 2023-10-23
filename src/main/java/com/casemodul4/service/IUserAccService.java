package com.casemodul4.service;

import com.casemodul4.model.UserAcc;
import com.casemodul4.model.dto.UserAccDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserAccService extends UserDetailsService {
    Iterable<UserAcc> findAll();

    UserAccDTO findById(int id);

    boolean save(UserAcc userAcc);
    UserAcc saveUserAcc(UserAcc userAcc);

    UserAcc findByIdUserAcc(int id);


    void remove(int id);
    UserAcc getUserAccLogin1( String email, String password);
    UserAcc getUserAccLogin2(String username, String password);

    void changePassword(int id, String password);

//    UserDetails loadUserByUsername(String username);
}
