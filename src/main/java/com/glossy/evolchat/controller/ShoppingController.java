package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingController {

    @GetMapping("/shopping")
    public String shopping(Model model) {
        model.addAttribute("activePage", "shopping");
        return "index";
    }
}
