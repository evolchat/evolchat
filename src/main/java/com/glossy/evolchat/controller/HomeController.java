package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("activeCategory", "home");
        model.addAttribute("activePage", "home");
        model.addAttribute("contentFragment", "fragments/home");
        return "index";
    }
}
