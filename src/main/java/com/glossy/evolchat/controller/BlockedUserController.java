package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.BlockedUser;
import com.glossy.evolchat.service.BlockedUserService;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class BlockedUserController {

    @Autowired
    private BlockedUserService blockedUserService;

    @Autowired
    private UserService userService;

    @PostMapping("/unblock")
    public String unblockUser(@RequestParam int blockedId, Principal principal) {
        int currentUserId = userService.findByUsername(principal.getName()).getId();
        blockedUserService.unblockUser(currentUserId, blockedId);
        return "redirect:/messenger_blocklists";
    }
}
