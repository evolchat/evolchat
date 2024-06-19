package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AjaxPageController {
    @GetMapping("/home_page")
    public String home_page() {
        return "home";
    }

    @GetMapping("/community_free_page")
    public String community_free_page() {
        return "community_free";
    }

    @GetMapping("/community_photo_page")
    public String community_photo_page() {
        return "community_photo";
    }

    @GetMapping("/messenger_add_friend_page")
    public String messenger_add_friend() {
        return "messenger_add_friend";
    }

    @GetMapping("/messenger_add_friend_wait_page")
    public String messenger_add_friend_wait_page() {
        return "messenger_add_friend_wait";
    }

    @GetMapping("/messenger_blocklists_page")
    public String messenger_blocklists_page() {
        return "messenger_blocklists";
    }

    @GetMapping("/messenger_friend_page")
    public String messenger_friend_page() {
        return "messenger_friend";
    }

    @GetMapping("/messenger_message_page")
    public String messenger_message_page() {
        return "messenger_message";
    }

    @GetMapping("/openchat_page")
    public String openchat_page() {
        return "openchat";
    }

    @GetMapping("/rank_page")
    public String rank_page() {
        return "rank";
    }
}