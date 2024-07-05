package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {
    @GetMapping("/mypqge")
    public String mypqge(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "mypqge");
        return "index";
    }

    @GetMapping("/my_myitem")
    public String my_myitem(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_myitem");
        return "index";
    }

    @GetMapping("/my_mycash")
    public String my_mycash(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_mycash");
        return "index";
    }

    @GetMapping("/my_activitypoints")
    public String my_activitypoints(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_activitypoints");
        return "index";
    }
    @GetMapping("/my_goldchip")
    public String my_goldchip(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_goldchip");
        return "index";
    }
    @GetMapping("/my_mypqge")
    public String my_mypqge(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_mypqge");
        return "index";
    }
}
