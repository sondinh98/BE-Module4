package com.casemodul4.controller;

import com.casemodul4.model.Role;
import com.casemodul4.model.UserAcc;
import com.casemodul4.model.dto.UserAccToken;
import com.casemodul4.service.IUserAccService;
import com.casemodul4.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class Login {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    IUserAccService userAccService;

    @PostMapping("/login")
    public UserAccToken getLogin(@RequestBody UserAcc userAcc){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAcc.getUsername(), userAcc.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        userAcc = userAccService.getUserAccLogin2(userAcc.getUsername(), userAcc.getPassword());
        String token = jwtService.createToken(authentication);
        UserAccToken userAccToken = new UserAccToken(userAcc.getId(), userAcc.getUsername(), userAcc.getEmail(), userAcc.getFullName(), userAcc.getAvatar(),userAcc.getCoverPhoto(),userAcc.getDescription(),userAcc.getRole(),token);
        return userAccToken;
    }
}
