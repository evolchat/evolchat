package com.glossy.evolchat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManagerBean;

    public LoginController(AuthenticationManager authenticationManagerBean) {
        this.authenticationManagerBean = authenticationManagerBean;
    }

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
            return "redirect:";
        }

        return "login";
    }

//    @PostMapping("/login")
//    public String performLogin(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password,
//            Model model,
//            HttpSession session) {
//        try {
//            Authentication authentication = authenticationManagerBean.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // 세션에 사용자 이름 저장
//            session.setAttribute("username", username);
//
//            System.out.println("Username saved in session: " + username);
//
//            return "redirect:/"; // 로그인 후 리디렉션할 경로
//        } catch (BadCredentialsException e) {
//            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
//            return "login";
//        } catch (Exception e) {
//            model.addAttribute("error", "로그인 중 오류가 발생했습니다.");
//            return "login";
//        }
//    }
}
