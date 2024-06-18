package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonPageController {
    @GetMapping("/header")
    public String header() {
        return "/include/header";
    }

    @GetMapping("/nav")
    public String nav() {
        return "/include/nav";
    }

    @GetMapping("/chat")
    public String chat() {
        return "/include/chat";
    }

    @GetMapping("/footer")
    public String footer() {
        return "/include/footer";
    }

}