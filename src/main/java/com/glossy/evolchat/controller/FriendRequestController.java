package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.FriendRequestDTO;
import com.glossy.evolchat.model.FriendRequest;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.FriendRequestService;
import com.glossy.evolchat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    public FriendRequestController(FriendRequestService friendRequestService, UserService userService) {
        this.friendRequestService = friendRequestService;
        this.userService = userService;
    }

    @GetMapping("/friend-requests")
    @ResponseBody
    public List<FriendRequestDTO> getPendingRequests(Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<FriendRequest> pendingRequests = friendRequestService.getPendingRequests(currentUser);

        // Convert to DTOs
        return pendingRequests.stream()
                .map(request -> new FriendRequestDTO(
                        request.getId(),
                        request.getReceiver().getNickname(), // Assuming `getReceiver()` returns the `User` who received the request
                        request.getReceiver().getTodaysMessage() // Use appropriate getter if message is part of FriendRequest
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
                response.put("message", "존재하지 않는 사용자입니다.");
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
}
