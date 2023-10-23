package com.casemodul4.repository.friend;

import com.casemodul4.model.Friend;
import com.casemodul4.model.UserAcc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    //truy vấn check tk 1 có là bạn với tk2 hay không:
//    Friend findByUserAccAndFriend(UserAcc userAcc, UserAcc friend);
    //truy vấn check tk 1 có phải là bạn với tk 2 hay không và ngược lại:
    @Query("SELECT f FROM Friend f WHERE (f.userAcc = :user1 AND f.friend = :user2) OR (f.userAcc = :user2 AND f.friend = :user1)")
    Friend findFriendship(UserAcc user1, UserAcc user2);
    List<Friend> findAllByUserAccOrFriend(UserAcc userAcc1, UserAcc userAcc2);
    Friend findByUserAccOrFriend(UserAcc userAcc1, UserAcc userAcc2);

    List<Friend> findAllByFriendAndStatus(UserAcc loggedInUser, byte b);
}
