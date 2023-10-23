package com.casemodul4.controller.friend;

import com.casemodul4.model.Friend;
import com.casemodul4.model.UserAcc;

import com.casemodul4.model.dto.friendDTO.DeleteFriendRequestDTO;
import com.casemodul4.model.dto.friendDTO.FriendInfoDTO;
import com.casemodul4.model.dto.friendDTO.FriendRequestDTO;
import com.casemodul4.repository.IUserAccRepo;
import com.casemodul4.repository.friend.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin("*")
@RestController

@RequestMapping("/api")
public class FriendController {
    @Autowired
    private IUserAccRepo userAccRepo;

    @Autowired
    private FriendRepository friendRepository;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//api gửi lời mời kết bạn
@PostMapping("/sendAddFriend")
public ResponseEntity<String> friendRequest(@RequestBody FriendRequestDTO friendRequestDTO) {
    UserAcc sender = userAccRepo.findById(friendRequestDTO.getSenderId()).orElse(null);
    UserAcc receiver = userAccRepo.findById(friendRequestDTO.getReceiverId()).orElse(null);

    if (sender == null || receiver == null) {
        return ResponseEntity.notFound().build();
    }

    // Kiểm tra nếu đã là bạn bè trước đó thì không gửi lại lời mời kết bạn
    Friend existingFriendship1 = friendRepository.findFriendship(sender, receiver);
    Friend existingFriendship2 = friendRepository.findFriendship(receiver, sender);
    if (existingFriendship1 != null && existingFriendship1.getStatus() == 1 &&
            existingFriendship2 != null && existingFriendship2.getStatus() == 1) {
        return ResponseEntity.ok().body("Hai tài khoản đã là bạn bè.");
    }

    // Kiểm tra nếu đã gửi lời mời trước đó thì không gửi lại
    if (existingFriendship1 != null && existingFriendship1.getStatus() == 0) {
        return ResponseEntity.ok().body("Đã gửi lời kết bạn trước đó.");
    }

    // Gửi lời mời kết bạn
    Friend friendship = new Friend();
    friendship.setUserAcc(sender);
    friendship.setFriend(receiver);
    friendship.setStatus((byte) 0); // Mặc định là pending khi gửi yêu cầu kết bạn
    friendRepository.save(friendship);

//    // Gửi thông báo WebSocket đến người nhận lời mời kết bạn
//    messagingTemplate.convertAndSendToUser(
//            Integer.toString(receiver.getId()), // Chuyển sang kiểu Integer và sau đó chuyển sang kiểu String
//            "/topic/friendRequests",
//            "Bạn có một lời mời kết bạn mới từ " + sender.getUsername()
//    );

    return ResponseEntity.ok().body("Đã gửi lời kết bạn thành công.");
}

    //api đồng ý kết bạn
    @PostMapping("/acceptFriendRequest")
    public ResponseEntity<Map<String, Object>> acceptFriendRequest(@RequestBody FriendRequestDTO friendRequestDTO) {
        UserAcc sender = userAccRepo.findById(friendRequestDTO.getSenderId()).orElse(null);
        UserAcc receiver = userAccRepo.findById(friendRequestDTO.getReceiverId()).orElse(null);
        if (sender == null || receiver == null) {
            return ResponseEntity.notFound().build();
        }
        Friend friendship = friendRepository.findFriendship(sender, receiver);
        if (friendship == null || friendship.getStatus() != 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Không tìm thấy yêu cầu kết bạn chờ xử lý.");
            return ResponseEntity.ok(response);
        }
        friendship.setStatus((byte) 1); // Cập nhật trạng thái từ "pending" sang "accepted"
        friendRepository.save(friendship);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Đồng ý kết bạn thành công.");
        return ResponseEntity.ok(response);
    }

    //api lấy danh sách các bạn bè của 1 tài khoản
    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<FriendInfoDTO>> getFriends(@PathVariable("userId") Integer userId) {
        List<FriendInfoDTO> friendInfoList = new ArrayList<>();
        // Tìm tài khoản của người dùng dựa trên userId
        UserAcc user = userAccRepo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        // Lấy danh sách bạn bè của người dùng
        List<Friend> friends = friendRepository.findAllByUserAccOrFriend(user, user);
        // Duyệt qua danh sách bạn bè và lấy thông tin cần trả về
        for (Friend friend : friends) {
            UserAcc friendUser = friend.getUserAcc().equals(user) ? friend.getFriend() : friend.getUserAcc();
            FriendInfoDTO friendInfo = new FriendInfoDTO();
            friendInfo.setAvatar(friendUser.getAvatar());
            friendInfo.setFullName(friendUser.getFullName());
            friendInfo.setDescription(friendUser.getDescription());
            friendInfoList.add(friendInfo);
        }
        return ResponseEntity.ok(friendInfoList);
    }
//api lấy danh sách các tài khoản có trong hệ thống
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserAcc>> getAllUsers() {
        List<UserAcc> allUsers = userAccRepo.findAll();
        return ResponseEntity.ok(allUsers);
    }
    //api kiểm tra 1 tài khoản trong danh sách các tài khoản đang có
    // trong hệ thống đã là bạn bè với tài khoản đang được đăng nhập hay chưa
    @GetMapping("/checkFriendship")
    public ResponseEntity<String> checkFriendship(@RequestParam("userId1") Integer userId1, @RequestParam("userId2") Integer userId2) {
        UserAcc user1 = userAccRepo.findById(userId1).orElse(null);
        UserAcc user2 = userAccRepo.findById(userId2).orElse(null);
        if (user1 == null || user2 == null) {
            return ResponseEntity.notFound().build();
        }
        Friend friendship = friendRepository.findFriendship(user1, user2);
        if (friendship != null && friendship.getStatus() == 1) {
            return ResponseEntity.ok("Hai tài khoản là bạn bè.");
        } else {
            return ResponseEntity.ok("Hai tài khoản không là bạn bè.");
        }
    }
    //api huy ket bạn

    @PostMapping("/unfriend")
    public ResponseEntity<String> unfriend(@RequestBody FriendRequestDTO friendRequestDTO) {
        UserAcc user1 = userAccRepo.findById(friendRequestDTO.getSenderId()).orElse(null);
        UserAcc user2 = userAccRepo.findById(friendRequestDTO.getReceiverId()).orElse(null);
        if (user1 == null || user2 == null) {
            return ResponseEntity.notFound().build();
        }
        Friend friendship = friendRepository.findFriendship(user1, user2);
        if (friendship != null && friendship.getStatus() == 1) {
            // Xóa mối quan hệ bạn bè
            friendRepository.delete(friendship);
            return ResponseEntity.ok("Hủy kết bạn thành công.");
        } else {
            return ResponseEntity.ok().body("Hai tài khoản không là bạn bè.");
        }
    }
    //api trả về các lời mời kết bạn mà tài khoản đang đăng nhập nhận được
    @GetMapping("/friendRequests")
    public ResponseEntity<List<Friend>> getFriendRequestsOfLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        String loggedInUsername = userDetails.getUsername();
        UserAcc loggedInUser = userAccRepo.findByUsername(loggedInUsername);
        System.out.println(loggedInUsername);
        // Lấy danh sách các lời mời kết bạn có trạng thái "pending" và có user là người nhận là tài khoản đang đăng nhập
        List<Friend> friendRequests = friendRepository.findAllByFriendAndStatus(loggedInUser, (byte) 0);
        // Kiểm tra nếu danh sách rỗng, trả về ResponseEntity với danh sách lời mời trống
        if (friendRequests.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(friendRequests);
    }
//api xóa 1 lời mời kết bạn mà tài khoản đang đăng nhâp nhận được
@PostMapping("/delFriendRequests")
public ResponseEntity<Map<String, Object>> deleteFriendRequest(@AuthenticationPrincipal UserDetails userDetails, @RequestBody FriendRequestDTO friendRequestDTO) {
    String loggedInUsername = userDetails.getUsername();
    UserAcc loggedInUser = userAccRepo.findByUsername(loggedInUsername);

    UserAcc sender = userAccRepo.findById(friendRequestDTO.getSenderId()).orElse(null);
    UserAcc receiver = userAccRepo.findById(friendRequestDTO.getReceiverId()).orElse(null);
    if (sender == null || receiver == null) {
        return ResponseEntity.notFound().build();
    }
    // Kiểm tra xem lời mời kết bạn có tồn tại và có được gửi đến người dùng đang đăng nhập không
    Friend friendship = friendRepository.findByUserAccOrFriend(sender, receiver);
    if (friendship == null || !friendship.getFriend().equals(loggedInUser)) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Không tìm thấy lời mời kết bạn.");
        return ResponseEntity.ok(response);
    }

    // Xóa lời mời kết bạn
    friendRepository.delete(friendship);
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Đã xóa lời mời kết bạn thành công.");
    return ResponseEntity.ok(response);
}

}

