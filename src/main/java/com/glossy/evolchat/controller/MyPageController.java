package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {
    @GetMapping("/my_pqge")
    public String my_pqge(Model model) {
        model.addAttribute("activeCategory", "my_pqge");
        model.addAttribute("activePage", "my_pqge");
        model.addAttribute("contentFragment", "fragments/my_pqge");
        return "index";
    }

    @GetMapping("/my_item")
    public String my_item(Model model) {
        model.addAttribute("activeCategory", "my_pqge");
        model.addAttribute("activePage", "my_item");
        model.addAttribute("contentFragment", "fragments/my_item");
        return "index";
    }

    @GetMapping("/my_cash")
    public String my_cash(Model model) {
        model.addAttribute("activeCategory", "my_pqge");
        model.addAttribute("activePage", "my_cash");
        model.addAttribute("contentFragment", "fragments/my_cash");
        return "index";
    }

    @GetMapping("/my_bettingpoints")
    public String my_bettingpoints(Model model) {
        model.addAttribute("activeCategory", "my_pqge");
        model.addAttribute("activePage", "my_bettingpoints");
        model.addAttribute("contentFragment", "fragments/my_bettingpoints");
        return "index";
    }


    @GetMapping("/my_activitypoints")
    public String my_activitypoints(Model model) {
        model.addAttribute("activeCategory", "my_pqge");
        model.addAttribute("activePage", "my_activitypoints");
        model.addAttribute("contentFragment", "fragments/my_activitypoints");
        return "index";
    }
    @GetMapping("/my_goldchip")
    public String my_goldchip(Model model) {
        model.addAttribute("activeCategory", "my_pqge");
        model.addAttribute("activePage", "my_goldchip");
        model.addAttribute("contentFragment", "fragments/my_goldchip");
        return "index";
    }
    @GetMapping("/my_mypqge")
    public String my_mypqge(Model model) {
        model.addAttribute("activeCategory", "my_pqge");
        model.addAttribute("activePage", "my_mypqge");
        model.addAttribute("contentFragment", "fragments/my_mypqge");
        return "index";
    }
}
