package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VerificationController {

    @PostMapping("/verify-phone")
    @ResponseBody
    public String verifyPhone(@RequestBody String phoneNumber) {
        System.out.println("휴대폰 번호: " + phoneNumber);
        return "인증 요청이 성공적으로 처리되었습니다.";
    }
}
