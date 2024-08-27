package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.ItemPurchaseSummary;
import com.glossy.evolchat.model.Purchase;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.repository.PurchaseRepository;
import com.glossy.evolchat.service.PurchaseService;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional; // Add this import

@Controller
public class MyPageController {
    @Autowired
    private UserService userService; // Assume a UserService that fetches user data

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping("/my_page")
    public String my_page(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (username != null) {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                model.addAttribute("user", user);
                Optional<UserPoints> userPointsOptional = userService.getUserPointsByUsername(username);
                if (userPointsOptional.isPresent()) {
                    model.addAttribute("userPoints", userPointsOptional.get());
                } else {
                    model.addAttribute("userPoints", new UserPoints()); // Handle the case where UserPoints is not found
                    model.addAttribute("error", "User points not found.");
                }
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
