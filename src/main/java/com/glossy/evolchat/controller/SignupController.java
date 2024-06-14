package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute("user") User user, Model model) {
        // 비밀번호 확인
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "signup";
        }

        // 아이디 중복 확인
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "이미 사용중인 아이디입니다.");
            return "signup";
        }

        // 이메일 중복 확인
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "이미 등록된 이메일입니다.");
            return "signup";
        }

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 사용자 저장
        userRepository.save(user);

        return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 이동
    }
}
