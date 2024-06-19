package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {

    @GetMapping("/community_free")
    public String community_free(Model model) {
        model.addAttribute("activePage", "community_free");
        return "index";
    }
    @GetMapping("/community_photo")
    public String community_photo(Model model) {
        model.addAttribute("activePage", "community_photo");
        return "index";
    }
}
