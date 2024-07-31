package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.SupportComment;
import com.glossy.evolchat.model.SupportPost;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class SupportController {

    @Autowired
    private SupportPostRepository supportPostRepository;

    @Autowired
    private SupportCommentRepository supportCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/support_detail")
    public String supportDetail(@RequestParam("postId") int postId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<SupportPost> postOptional = supportPostRepository.findById(postId);
        if (postOptional.isPresent()) {
            SupportPost supportPost = postOptional.get();

            // SupportPost 객체를 전달하여 댓글 조회
            List<SupportComment> supportComments = supportCommentRepository.findBySupportPost(supportPost);

            Optional<User> authorOptional = userRepository.findByUsername(supportPost.getUserId());
            String authorNickname = authorOptional.map(User::getNickname).orElse("Unknown");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            String formattedDate = supportPost.getCreatedAt().format(formatter);

            model.addAttribute("supportPost", supportPost);
            model.addAttribute("comments", supportComments != null ? supportComments : List.of());
            model.addAttribute("authorNickname", authorNickname);
            model.addAttribute("formattedDate", formattedDate);
            model.addAttribute("activeCategory", "support_free");
            model.addAttribute("activePage", "support_detail");
            model.addAttribute("contentFragment", "fragments/support_detail");

            // 사용자 정보를 모델에 추가
            Optional<User> currentUserOptional = userRepository.findByUsername(userDetails.getUsername());
            currentUserOptional.ifPresent(user -> model.addAttribute("user", user));
        } else {
            return "error/404";
        }
        return "index";
    }

    @GetMapping("/support_notice")
    public String supportNotice(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_notice");
        model.addAttribute("contentFragment", "fragments/support_notice");
        return "index";
    }

    @GetMapping("/support_help")
    public String supportHelp(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_help");
        model.addAttribute("contentFragment", "fragments/support_help");
        return "index";
    }

    @GetMapping("/support_support")
    public String supportSupport(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_support");
        model.addAttribute("contentFragment", "fragments/support_support");
        return "index";
    }

    @GetMapping("/support_inquiry")
    public String supportInquiry(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_inquiry");
        model.addAttribute("contentFragment", "fragments/support_inquiry");
        return "index";
    }

    @GetMapping("/support_inquiry_write")
    public String supportInquiryWrite(Model model) {
        model.addAttribute("activeCategory", "support_notice");
        model.addAttribute("activePage", "support_inquiry_write");
        model.addAttribute("contentFragment", "fragments/support_inquiry_write");
        return "index";
    }
}
