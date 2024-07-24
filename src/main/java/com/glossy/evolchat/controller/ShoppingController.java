package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingController {

    @GetMapping("/shopping")
    public String shopping(Model model) {
        model.addAttribute("activeCategory", "shopping");
        model.addAttribute("activePage", "shopping");
        model.addAttribute("contentFragment", "fragments/shopping");
        return "index";
    }
    @GetMapping("/shopping_chat")
    public String shopping_chat(Model model) {
        model.addAttribute("activeCategory", "shopping");
        model.addAttribute("activePage", "shopping_chat");
        model.addAttribute("contentFragment", "fragments/shopping_chat");
        return "index";
    }
    @GetMapping("/shopping_decorate")
    public String shopping_decorate(Model model) {
        model.addAttribute("activeCategory", "shopping");
        model.addAttribute("activePage", "shopping_decorate");
        model.addAttribute("contentFragment", "fragments/shopping_decorate");
        return "index";
    }
    @GetMapping("/shopping_point")
    public String shopping_point(Model model) {
        model.addAttribute("activeCategory", "shopping");
        model.addAttribute("activePage", "shopping_point");
        model.addAttribute("contentFragment", "fragments/shopping_point");
        return "index";
    }
    @GetMapping("/shopping_goldchip")
    public String shopping_goldchip(Model model) {
        model.addAttribute("activeCategory", "shopping");
        model.addAttribute("activePage", "shopping_goldchip");
        model.addAttribute("contentFragment", "fragments/shopping_goldchip");
        return "index";
    }
    @GetMapping("/shopping_etc")
    public String shopping_etc(Model model) {
        model.addAttribute("activeCategory", "shopping");
        model.addAttribute("activePage", "shopping_etc");
        model.addAttribute("contentFragment", "fragments/shopping_etc");
        return "index";
    }
}
