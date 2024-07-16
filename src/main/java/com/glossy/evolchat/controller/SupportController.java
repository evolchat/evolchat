package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {

    @GetMapping("/support_notice")
    public String support_notice(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_notice");
        return "index";
    }

    @GetMapping("/support_help")
    public String support_help(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_help");
        return "index";
    }

    @GetMapping("/support_support")
    public String support_support(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_support");
        return "index";
    }

    @GetMapping("/support_inquiry")
    public String support_inquiry(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_inquiry");
        return "index";
    }

    @GetMapping("/support_inquiry_write")
    public String support_inquiry_write(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_inquiry_write");
        return "index";
    }
}
