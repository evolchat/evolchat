package com.glossy.evolchat.controller;
;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SessionController {
    @GetMapping("/session-expired")
    public String sessionExpired(Model model) {
        return "login";
    }
}
