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
        model.addAttribute("contentFragment", "fragments/mypqge");
        return "index";
    }

    @GetMapping("/my_myitem")
    public String my_myitem(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_myitem");
        model.addAttribute("contentFragment", "fragments/my_myitem");
        return "index";
    }

    @GetMapping("/my_mycash")
    public String my_mycash(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_mycash");
        model.addAttribute("contentFragment", "fragments/my_mycash");
        return "index";
    }

    @GetMapping("/my_bettingpoints")
    public String my_bettingpoints(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_bettingpoints");
        model.addAttribute("contentFragment", "fragments/my_bettingpoints");
        return "index";
    }


    @GetMapping("/my_activitypoints")
    public String my_activitypoints(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_activitypoints");
        model.addAttribute("contentFragment", "fragments/my_activitypoints");
        return "index";
    }
    @GetMapping("/my_goldchip")
    public String my_goldchip(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_goldchip");
        model.addAttribute("contentFragment", "fragments/my_goldchip");
        return "index";
    }
    @GetMapping("/my_mypqge")
    public String my_mypqge(Model model) {
        model.addAttribute("activeCategory", "mypqge");
        model.addAttribute("activePage", "my_mypqge");
        model.addAttribute("contentFragment", "fragments/my_mypqge");
        return "index";
    }
}
