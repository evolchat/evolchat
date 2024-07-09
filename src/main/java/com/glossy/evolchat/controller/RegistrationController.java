package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.UserPointsService;
import com.glossy.evolchat.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserPointsService userPointsService;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserPointsService userPointsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userPointsService = userPointsService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
//        CPClient niceCheck = new CPClient();
//
//        String sSiteCode = "5c34b918-d3e3-446d-a0ea-66e3a9cb520c";            // NICE로부터 부여받은 사이트 코드
//        String sSitePassword = "1be8f303b795af46abdbdb3459cfad50";        // NICE로부터 부여받은 사이트 패스워드
//
//        String sRequestNumber = niceCheck.getRequestNO(sSiteCode);        // 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로
//
//        String sAuthType = "M";        // 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
//
//        String popgubun = "N";        //Y : 취소버튼 있음 / N : 취소버튼 없음
//        String customize = "";        // 없으면 기본 웹페이지 / Mobile : 모바일페이지
//        String sGender = "";            // 없으면 기본 선택 값, 0 : 여자, 1 : 남자
//
//        String sReturnUrl = "http://localhost:8080/pass/success"; // 성공시 이동될 URL
//        String sErrorUrl = "http://localhost:8080/pass/fail"; // 실패시 이동될 URL
//
//        // 입력될 plain 데이타를 만든다.
//        String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
//                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
//                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
//                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
//                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
//                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
//                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
//                "6:GENDER" + sGender.getBytes().length + ":" + sGender;
//
//        String sMessage = "";
//        String sEncData = "";
//
//        int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
//        if (iReturn == 0) {
//            sEncData = niceCheck.getCipherData();
//        } else if (iReturn == -1) {
//            sMessage = "암호화 시스템 에러입니다.";
//        } else if (iReturn == -2) {
//            sMessage = "암호화 처리오류입니다.";
//        } else if (iReturn == -3) {
//            sMessage = "암호화 데이터 오류입니다.";
//        } else if (iReturn == -9) {
//            sMessage = "입력 데이터 오류입니다.";
//        } else {
//            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
//        }
//
//        request.getSession().setAttribute("REQ_SEQ", sRequestNumber);
//
//        modelMap.addAttribute("sMessage", sMessage);
//        modelMap.addAttribute("sEncData", sEncData);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, RedirectAttributes redirectAttributes) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // 회원가입 후 초기 포인트 설정
        userPointsService.initializeUserPoints(savedUser.getUsername());

        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 로그인해주세요.");
        return "redirect:/login";
    }
}
