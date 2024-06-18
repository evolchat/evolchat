package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {

    @GetMapping("/support_notice")
    public String support_notice(Model model) {
        model.addAttribute("activePage", "support_notice");
        return "support_notice";
    }

    @GetMapping("/support_help")
    public String support_help(Model model) {
        model.addAttribute("activePage", "support_notice");
        return "support_help";
    }

    @GetMapping("/support_support")
    public String support_support(Model model) {
        model.addAttribute("activePage", "support_notice");
        return "support_support";
    }
}
