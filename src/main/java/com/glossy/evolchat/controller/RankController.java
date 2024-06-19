package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RankController {

    @GetMapping("/rank")
    public String rank(Model model) {
        model.addAttribute("activePage", "rank");
        return "index";
    }
}
