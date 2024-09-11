package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.FriendRequestDTO;
import com.glossy.evolchat.model.Friend;
import com.glossy.evolchat.model.FriendRequest;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.FriendRequestService;
import com.glossy.evolchat.service.FriendService;
import com.glossy.evolchat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/messenger")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final UserService userService;
    private final FriendService friendService;

    public FriendRequestController(FriendRequestService friendRequestService, UserService userService, FriendService friendService) {
        this.friendRequestService = friendRequestService;
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping("/friend-requests")
    @ResponseBody
    public List<FriendRequestDTO> getPendingRequests(Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<FriendRequest> pendingRequests = friendRequestService.getPendingRequests(currentUser);

        return pendingRequests.stream()
                .map(request -> new FriendRequestDTO(
                        request.getId(),
                        request.getSender().getNickname(),
                        request.getReceiver().getNickname(),
                        request.getSender().getTodaysMessage(),
                        request.getSender().equals(currentUser)
                ))
                .collect(Collectors.toList());
    }

    @PostMapping("/send-request")
    public ResponseEntity<Map<String, Object>> sendFriendRequest(@RequestParam String receiverUsername, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            User sender = userService.findByUsername(principal.getName());
            User receiver = userService.getUserByNickname(receiverUsername);

            if (receiver == null) {
                response.put("success", false);
                response.put("errorType", "USER_NOT_FOUND");
                response.put("message", "존재하지 않는 사용자입니다.");
                return ResponseEntity.ok(response);
            }

            if (receiver.equals(sender)) {
                response.put("success", false);
                response.put("errorType", "SELF_REQUEST");
                response.put("message", "자기 자신에게는 친구 요청을 보낼 수 없습니다.");
                return ResponseEntity.ok(response);
            }

            if (friendRequestService.isAlreadyFriend(sender, receiver)) {
                response.put("success", false);
                response.put("errorType", "ALREADY_FRIEND");
                response.put("message", "이미 친구입니다.");
                return ResponseEntity.ok(response);
            }

            if (friendRequestService.isRequestAlreadySent(sender, receiver)) {
                response.put("success", false);
                response.put("errorType", "ALREADY_SENT");
                response.put("message", "이미 친구 요청을 보냈습니다.");
                return ResponseEntity.ok(response);
            }

            friendRequestService.sendFriendRequest(sender, receiver);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "요청 처리 중 오류가 발생했습니다.");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/cancel-request")
    @ResponseBody
    public ResponseEntity<?> cancelFriendRequest(@RequestParam("requestId") Long requestId, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());

        boolean result = friendRequestService.cancelFriendRequest(requestId, currentUser);
        if (result) {
            return ResponseEntity.ok("{\"success\": true}");
        } else {
            return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"친구 요청 취소에 실패했습니다.\"}");
        }
    }

    @PostMapping("/accept-request")
    @ResponseBody
    public ResponseEntity<?> acceptFriendRequest(@RequestParam("requestId") Long requestId, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());

        boolean result = friendRequestService.acceptFriendRequest(requestId, currentUser);
        if (result) {
            return ResponseEntity.ok("{\"success\": true}");
        } else {
            return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"친구 요청 수락에 실패했습니다.\"}");
        }
    }

    @GetMapping("/friends")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getFriendsList(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<Friend> friends = friendService.getFriends(currentUser.getId());

        Map<String, Object> response = new HashMap<>();
        if (friends.isEmpty()) {
            response.put("success", true);
            response.put("friends", List.of());
        } else {
            List<Map<String, Object>> friendsList = friends.stream()
                    .map(friend -> {
                        Integer friendId = (friend.getUserId1().equals(currentUser.getId())) ? friend.getUserId2() : friend.getUserId1();
                        User friendUser = userService.getUserById(friendId);
                        Map<String, Object> friendInfo = new HashMap<>();
                        friendInfo.put("id", friend.getId());
                        friendInfo.put("nickname", friendUser.getNickname());
                        friendInfo.put("profileImg", friendUser.getProfilePicture());
                        friendInfo.put("status", friendUser.getTodaysMessage());
                        return friendInfo;
                    })
                    .filter(friendInfo -> friendInfo.get("nickname").toString().toLowerCase().contains(query.toLowerCase())) // 필터링
                    .collect(Collectors.toList());

            response.put("success", true);
            response.put("friends", friendsList);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/unblock-user")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> unblockUser(@RequestParam("userId") Integer userId, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            User currentUser = userService.findByUsername(principal.getName());
            friendService.unblockUser(currentUser.getId(), userId);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "차단 해제 처리 중 오류가 발생했습니다.");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/blocked-users")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBlockedUsers(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<Friend> blockedUsers = friendService.getBlocklist(currentUser.getId());

        Map<String, Object> response = new HashMap<>();
        if (blockedUsers.isEmpty()) {
            response.put("success", true);
            response.put("blockedUsers", List.of());
        } else {
            List<Map<String, Object>> blockedUsersList = blockedUsers.stream()
                    .map(friend -> {
                        Integer blockedUserId = (friend.getUserId1().equals(currentUser.getId())) ? friend.getUserId2() : friend.getUserId1();
                        User blockedUser = userService.getUserById(blockedUserId);
                        Map<String, Object> blockedUserInfo = new HashMap<>();
                        blockedUserInfo.put("id", blockedUser.getId());
                        blockedUserInfo.put("nickname", blockedUser.getNickname());
                        blockedUserInfo.put("profileImg", blockedUser.getProfilePicture());
                        blockedUserInfo.put("status", blockedUser.getTodaysMessage());
                        return blockedUserInfo;
                    })
                    .filter(blockedUserInfo -> blockedUserInfo.get("nickname").toString().toLowerCase().contains(query.toLowerCase())) // 필터링
                    .collect(Collectors.toList());

            response.put("success", true);
            response.put("blockedUsers", blockedUsersList);
        }

        return ResponseEntity.ok(response);
    }
}
