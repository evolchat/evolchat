package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/find-id")
    public String find_id(Model model) {
        return "find-id";
    }

    @GetMapping("/find-password")
    public String find_password(Model model) {
        return "find-password";
    }
}
