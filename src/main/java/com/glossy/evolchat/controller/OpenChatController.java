package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenChatController {

    @GetMapping("/openchat")
    public String openchat(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat");
        return "index";
    }
}
