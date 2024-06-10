package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FindIdController {

    private final UserRepository userRepository;

    public FindIdController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/find-id")
    public String showFindIdForm() {
        return "find-id";
    }

    @PostMapping("/find-id")
    public String findId(String email, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        } else {
            model.addAttribute("error", "이메일을 찾을 수 없습니다.");
        }
        return "find-id";
    }
}
