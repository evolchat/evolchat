package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessengerController {

    @GetMapping("/messenger_friend")
    public String messenger_friend(Model model) {
        model.addAttribute("activeCategory", "messenger_friend");
        model.addAttribute("activePage", "messenger_friend");
        model.addAttribute("contentFragment", "fragments/messenger_friend");
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
}
