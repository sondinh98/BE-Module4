package com.casemodul4.repository;

import com.casemodul4.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPostRepo extends JpaRepository<Post,Integer> {
    Optional<Post> findById(int id);
    @Query(nativeQuery = true ,value = "select * from post join user_acc on user_acc_id = user_acc.id where user_acc_id= :id order by post.id desc")
    List<Post> findAllPostByUserAccId(@Param("id") int id);
    List<Post> findByOrderByIdDesc();

}
