package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.Friend;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.FriendService;
import com.glossy.evolchat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MessengerController {

    private final UserService userService;
    private final FriendService friendService;

    public MessengerController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping("/messenger_friend")
    public String messenger_friend(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<Friend> friends = friendService.getId1OrId2Friends(currentUser.getId());

        model.addAttribute("activeCategory", "messenger_friend");
        model.addAttribute("activePage", "messenger_friend");
        model.addAttribute("contentFragment", "fragments/messenger_friend");
        model.addAttribute("friends", friends);
        return "index";
    }

    @GetMapping("/messenger_message")
    public String messenger_message(Model model) {
        model.addAttribute("activeCategory", "messenger_friend");
        model.addAttribute("activePage", "messenger_message");
        model.addAttribute("contentFragment", "fragments/messenger_message");
        return "index";
    }
    @GetMapping("/messenger_blocklists")
    public String messenger_blocklists(Model model) {
        model.addAttribute("activeCategory", "messenger_friend");
        model.addAttribute("activePage", "messenger_blocklists");
        model.addAttribute("contentFragment", "fragments/messenger_blocklists");
        return "index";
    }

    @GetMapping("/messenger_add_friend")
    public String messenger_add_friend(Model model) {
        model.addAttribute("activeCategory", "messenger_friend");
        model.addAttribute("activePage", "messenger_add_friend");
        model.addAttribute("contentFragment", "fragments/messenger_add_friend");
        return "index";
    }

    @GetMapping("/messenger_add_friend_wait")
    public String messenger_add_friend_wait(Model model) {
        model.addAttribute("activeCategory", "messenger_friend");
        model.addAttribute("activePage", "messenger_add_friend_wait");
        model.addAttribute("contentFragment", "fragments/messenger_add_friend_wait");
        return "index";
    }
}
