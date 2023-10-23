package com.casemodul4.controller;

import com.casemodul4.model.Role;
import com.casemodul4.model.UserAcc;
import com.casemodul4.model.dto.UserAccDTO;
import com.casemodul4.repository.IUserAccRepo;
import com.casemodul4.repository.role.RoleRepository;
import com.casemodul4.service.IUserAccService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UserAccController {
    @Autowired
    IUserAccRepo iUserAccRepo;
    @Autowired
    IUserAccService iUserAccService;
    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/forgotPassword")
    public ResponseEntity<UserAcc> forgot(@RequestBody UserAcc userAcc) {
        UserAcc userAcc1 = iUserAccRepo.findAllByUsernameAndEmail(userAcc.getUsername(), userAcc.getEmail());
        if (userAcc1 != null)
            return new ResponseEntity<>(userAcc1, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/userAccDetail/{userAccId}")
    public ResponseEntity<UserAccDTO> findUserAccDTOById(@PathVariable int userAccId) {
        UserAccDTO userAccDTO = iUserAccService.findById(userAccId);
        if (userAccDTO != null) {
            return new ResponseEntity<>(userAccDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserAcc> createNewAcc(@RequestBody UserAcc userAcc) {
        userAcc.setAvatar("https://cdn.pixabay.com/photo/2014/03/24/13/49/avatar-294480_960_720.png");
        userAcc.setCoverPhoto("https://file.vfo.vn/hinh/2014/3/anh-bia-facebook-7.jpg");
        userAcc.setDescription("");
//        Role role = roleRepository.findByName("ROLE_USER");
//        userAcc.setRole(role);
        if (iUserAccService.save(userAcc))
            return new ResponseEntity<>(userAcc, HttpStatus.OK);
        else
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/editUserAcc/{userAccId}")
    public ResponseEntity<UserAcc> editUserAcc(@PathVariable int userAccId,
                                               @RequestBody UserAcc userAcc) {
        UserAcc userAcc1 = iUserAccService.findByIdUserAcc(userAccId);
        if (userAcc1 != null) {
            userAcc.setId(userAccId);
            userAcc.setPassword(userAcc1.getPassword());
            userAcc.setRole(userAcc1.getRole());
            iUserAccService.saveUserAcc(userAcc);
            return new ResponseEntity<>(userAcc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/editPassword/{userAccId}/{passworldNew}/{passwordOld}")
    public ResponseEntity<UserAcc> editPassword(@PathVariable String passworldNew,
                                                @PathVariable int userAccId,
                                                @PathVariable String passwordOld) {
        UserAcc userAcc1 = iUserAccService.findByIdUserAcc(userAccId);
        if (userAcc1 != null) {
            if (userAcc1.getPassword().equals(passwordOld)) {
                iUserAccService.changePassword(userAccId, passworldNew);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
