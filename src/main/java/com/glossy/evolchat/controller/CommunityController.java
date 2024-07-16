package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {

    @GetMapping("/community")
    public String community(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community");
        return "index";
    }
    @GetMapping("/community_detail")
    public String community_detail(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_detail");
        return "index";
    }
    @GetMapping("/community_free")
    public String community_free(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_free");
        return "index";
    }
    @GetMapping("/community_photo")
    public String community_photo(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_photo");
        return "index";
    }
    @GetMapping("/community_videos")
    public String community_videos(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_videos");
        return "index";
    }
    @GetMapping("/community_write")
    public String community_write(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_write");
        return "index";
    }
}
