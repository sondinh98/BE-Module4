package com.casemodul4.service.friend;


import com.casemodul4.model.Friend;
import com.casemodul4.repository.friend.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements IFriendService<Friend> {
    @Autowired
    FriendRepository friendRepository;

    @Override
    public Page<Friend> getAllListFriend() {
        return (Page<Friend>) friendRepository.findAll();
    }

    @Override
    public Friend findById(int id) {
return friendRepository.findById(id).get();
    }

    @Override
    public void save(Friend friend) {

    }

    @Override
    public void deleteById(int id) {

    }
}
