package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportNoticeController {

    @GetMapping("/support_notice")
    public String support_notice(Model model) {
        model.addAttribute("activePage", "support_notice");
        return "support_notice";
    }
}
