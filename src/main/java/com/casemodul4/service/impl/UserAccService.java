package com.casemodul4.service.impl;

import com.casemodul4.model.Role;
import com.casemodul4.model.UserAcc;
import com.casemodul4.model.dto.UserAccDTO;
import com.casemodul4.repository.IUserAccRepo;
import com.casemodul4.service.IUserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccService implements IUserAccService {
    @Autowired
    IUserAccRepo iUserAccRepo;

    @Override
    public Iterable<UserAcc> findAll() {
        return iUserAccRepo.findAll();
    }

    @Override
    public UserAccDTO findById(int id) {
        UserAcc userAccDTO = iUserAccRepo.getAllById(id) ;
        return userAccDTO.userAccDTO();
    }



    @Override
    public boolean save(UserAcc userAcc) {
        UserAcc userAcc1 = iUserAccRepo.findUserAccsByUsername(userAcc.getUsername());
        if (userAcc1 == null) {
            userAcc.setRole(new Role());
            userAcc.getRole().setId(2);
            iUserAccRepo.save(userAcc);
            return true;
        }
        return false;
    }

    @Override
    public UserAcc saveUserAcc(UserAcc userAcc) {
        return iUserAccRepo.save(userAcc);
    }

    @Override
    public UserAcc findByIdUserAcc(int id) {
        return iUserAccRepo.findById(id).get();
    }


    @Override
    public void remove(int id) {

        iUserAccRepo.deleteById(id);
    }

    @Override
    public UserAcc getUserAccLogin1(String email, String password) {
        return iUserAccRepo.getUserAccLogin1(email, password);
    }

    @Override
    public UserAcc getUserAccLogin2(String username, String password) {
        return iUserAccRepo.findByUsernameAndPassword(username, password);
    }

    @Transactional
    @Override
    public void changePassword(int id, String password) {

        UserAcc userAcc = iUserAccRepo.findById(id).get();
        if (userAcc != null) {
            userAcc.setPassword(password);
            iUserAccRepo.save(userAcc);
        } else {
            System.out.println("Fail");
        }

    }




    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAcc userAcc = iUserAccRepo.findUserAccsByUsername(username);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(userAcc.getRole());
        return new User(userAcc.getUsername(), userAcc.getPassword(), roles);
    }

}
