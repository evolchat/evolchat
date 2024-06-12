package com.glossy.evolchat.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
        }

        if (logout != null) {
            model.addAttribute("logout", "성공적으로 로그아웃 되었습니다.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            return "redirect:/home"; // 이미 로그인되어 있으면 홈 페이지로 리다이렉트
        }

        return "login";
    }
}
