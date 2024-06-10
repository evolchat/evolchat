package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FindPasswordController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public FindPasswordController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/find-password")
    public String showFindPasswordForm() {
        return "find-password";
    }

    @PostMapping("/find-password")
    public String findPassword(String email, String username, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getUsername().equals(username)) {
            // 임시 비밀번호 생성 및 이메일 전송 로직 추가 필요
            String tempPassword = "temporaryPassword"; // 임시 비밀번호 생성 로직 필요
            user.setPassword(passwordEncoder.encode(tempPassword));
            userRepository.save(user);
            model.addAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
        } else {
            model.addAttribute("error", "계정 정보를 찾을 수 없습니다.");
        }
        return "find-password";
    }
}
