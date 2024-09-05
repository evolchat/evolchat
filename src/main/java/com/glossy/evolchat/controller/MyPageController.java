package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CommunityPostDto;
import com.glossy.evolchat.dto.ItemPurchaseSummary;
import com.glossy.evolchat.model.*;
import com.glossy.evolchat.repository.PurchaseRepository;
import com.glossy.evolchat.service.MyPageService;
import com.glossy.evolchat.service.PurchaseService;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional; // Add this import
import java.util.stream.Collectors;

@Controller
public class MyPageController {
    @Autowired
    private UserService userService; // Assume a UserService that fetches user data

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private MyPageService myPageService;

    @GetMapping("/my_page")
    public String my_page(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (username != null) {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                model.addAttribute("user", user);

                // Fetch user points
                Optional<UserPoints> userPointsOptional = userService.getUserPointsByUsername(username);
                if (userPointsOptional.isPresent()) {
                    model.addAttribute("userPoints", userPointsOptional.get());
                } else {
                    model.addAttribute("userPoints", new UserPoints()); // Handle the case where UserPoints is not found
                    model.addAttribute("error", "User points not found.");
                }

                // Get the count of profile picture upload tickets (item_id = 2)
                int uploadTicketCount = purchaseService.getProfileUploadTicketCount(user);
                model.addAttribute("uploadTicketCount", uploadTicketCount);

                // Get the count of My Home background upload tickets (item_id = 3)
                int myHomeTicketCount = purchaseService.getMyHomeUploadTicketCount(user);
                model.addAttribute("myHomeTicketCount", myHomeTicketCount);

            } else {
                model.addAttribute("error", "User not found.");
            }
        } else {
            model.addAttribute("error", "User not authenticated.");
        }

        model.addAttribute("activeCategory", "my_page");
        model.addAttribute("activePage", "my_page");
        model.addAttribute("contentFragment", "fragments/my_page");

        return "index";
    }

    @PostMapping("/my_page/save")
    public String saveUserInfo(@RequestParam("username") String username,
                               @RequestParam(value = "profile", required = false) MultipartFile profilePicture,
                               @RequestParam(value = "background", required = false) MultipartFile backgroundPicture,
                               @RequestParam(value = "idCardUpload", required = false) MultipartFile idCardPicture,
                               @RequestParam("myHomeUrl") String myHomeUrl,
                               @RequestParam("todaysMessage") String todaysMessage,
                               @RequestParam("country") String country,
                               @RequestParam("region") String region,
                               @RequestParam("gender") String gender,
                               @RequestParam("locationPublic") boolean locationPublic,
                               @RequestParam("bankName") String bankName,
                               @RequestParam("accountNumber") String accountNumber,
                               @RequestParam("interests") String interests,
                               RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        User user = userService.getUserByUsername(currentUsername);
        if (user != null) {
            user.setNickname(username);
            user.setMyHomeUrl(myHomeUrl);
            user.setTodaysMessage(todaysMessage);
            user.setCountry(country);
            user.setRegion(region);
            user.setGender(gender);
            user.setLocationPublic(locationPublic);
            user.setBankName(bankName);
            user.setAccountNumber(accountNumber);
            user.setInterests(interests);

            try {
                myPageService.saveUserWithFiles(user, profilePicture, backgroundPicture, idCardPicture);
                return "redirect:/my_page"; // 성공 시 페이지 리다이렉트
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "파일 업로드 실패");
                return "redirect:/my_page";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "사용자를 찾을 수 없습니다.");
            return "redirect:/my_page";
        }
    }

    @GetMapping("/my_item")
    public String my_item(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        // 아이템별 총 보유 갯수 조회
        List<ItemPurchaseSummary> itemPurchaseSummaries = purchaseService.getItemPurchaseSummariesByUser(user);

        model.addAttribute("itemPurchaseSummaries", itemPurchaseSummaries);
        model.addAttribute("activeCategory", "my_page");
        model.addAttribute("activePage", "my_item");
        model.addAttribute("contentFragment", "fragments/my_item");
        return "index";
    }

    @GetMapping("/my_cash")
    public String my_cash(Model model) {
        model.addAttribute("activeCategory", "my_page");
        model.addAttribute("activePage", "my_cash");
        model.addAttribute("contentFragment", "fragments/my_cash");
        return "index";
    }

    @GetMapping("/my_bettingpoints")
    public String my_bettingpoints(Model model) {
        model.addAttribute("activeCategory", "my_page");
        model.addAttribute("activePage", "my_bettingpoints");
        model.addAttribute("contentFragment", "fragments/my_bettingpoints");
        return "index";
    }

    @GetMapping("/my_activitypoints")
    public String my_activitypoints(Model model) {
        model.addAttribute("activeCategory", "my_page");
        model.addAttribute("activePage", "my_activitypoints");
        model.addAttribute("contentFragment", "fragments/my_activitypoints");
        return "index";
    }
    @GetMapping("/my_goldchip")
    public String my_goldchip(Model model) {
        model.addAttribute("activeCategory", "my_page");
        model.addAttribute("activePage", "my_goldchip");
        model.addAttribute("contentFragment", "fragments/my_goldchip");
        return "index";
    }
}
