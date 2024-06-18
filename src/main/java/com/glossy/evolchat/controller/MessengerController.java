package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessengerController {

    @GetMapping("/messenger_friend")
    public String support_notice(Model model) {
        model.addAttribute("activePage", "messenger_friend");
        return "messenger_friend";
    }
}
