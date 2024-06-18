package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessengerController {

    @GetMapping("/messenger_friend")
    public String messenger_friend(Model model) {
        model.addAttribute("activePage", "messenger_friend");
        return "messenger_friend";
    }

    @GetMapping("/messenger_message")
    public String messenger_message(Model model) {
        model.addAttribute("activePage", "messenger_friend");
        return "messenger_message";
    }

    @GetMapping("/messenger_blocklists")
    public String messenger_blocklists(Model model) {
        model.addAttribute("activePage", "messenger_friend");
        return "messenger_blocklists";
    }

    @GetMapping("/messenger_add_friend")
    public String messenger_add_friend(Model model) {
        model.addAttribute("activePage", "messenger_friend");
        return "messenger_add_friend";
    }
}
