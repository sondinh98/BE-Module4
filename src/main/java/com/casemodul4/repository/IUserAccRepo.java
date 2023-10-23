package com.casemodul4.repository;

import com.casemodul4.model.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserAccRepo extends JpaRepository<UserAcc,Integer> {
    @Query(value = "SELECT  u FROM UserAcc u WHERE u.password LIKE :password AND  u.email LIKE :email")
    UserAcc getUserAccLogin1(@Param("password") String password, @Param("email") String email);

    //    @Query(value = "SELECT u from UserAcc u where u.username= :username and u.password= :password")
//    UserAcc getUserAccLogin2(@Param("username")String username, @Param("password") String password);
    UserAcc findByUsernameAndPassword(String username, String password);

    UserAcc findUserAccsByUsername(String username);
    UserAcc findAllByUsernameAndEmail(String username,String email);
    UserAcc findByUsername(String username);
    UserAcc getAllById(int id);



}

