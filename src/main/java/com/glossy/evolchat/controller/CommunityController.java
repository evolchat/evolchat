package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CommunityCommentDto;
import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.CommunityCommentRepository;
import com.glossy.evolchat.repository.CommunityPostLikeRepository;
import com.glossy.evolchat.repository.CommunityPostRepository;
import com.glossy.evolchat.repository.UserRepository;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CommunityController {

    @Autowired
    private CommunityPostRepository communityPostRepository;

    @Autowired
    private CommunityCommentRepository communityCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommunityPostLikeRepository communityPostLikeRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/community_detail")
    public String communityDetail(@RequestParam("postId") int postId, @RequestParam("boardId") int boardId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<CommunityPost> postOptional = communityPostRepository.findById(postId);
        if (postOptional.isPresent()) {
            CommunityPost communityPost = postOptional.get();

            // CommunityPost 객체를 전달하여 댓글 조회
            List<CommunityComment> communityComments = communityCommentRepository.findByCommunityPost(communityPost);

            // 댓글 DTO 리스트 생성
            List<CommunityCommentDto> commentDtos = communityComments.stream().map(comment -> {
                CommunityCommentDto dto = new CommunityCommentDto();
                dto.setId(comment.getId());
                dto.setPostId(communityPost.getPostId());
                dto.setContent(comment.getContent());
                dto.setCreatedAt(communityPost.getCreatedAt());
                dto.setUpdatedAt(communityPost.getUpdatedAt());

                // 댓글 작성자의 닉네임 조회
                dto.setUserNickname(comment.getUser().getNickname());

                return dto;
            }).collect(Collectors.toList());

            int commentCount = commentDtos.size();
            Optional<User> authorOptional = userRepository.findByUsername(communityPost.getUserId());
            String authorNickname = authorOptional.map(User::getNickname).orElse("Unknown");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            String formattedDate = communityPost.getCreatedAt().format(formatter);

            boolean isLiked = communityPostLikeRepository.findByPostIdAndUserId(postId, userDetails.getUsername()).isPresent();
            long postLikeCount = communityPostLikeRepository.countByPostId(postId);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByUsername(auth.getName());

            model.addAttribute("communityPost", communityPost);
            model.addAttribute("comments", commentDtos); // 변경된 부분: DTO 리스트 전달
            model.addAttribute("authorNickname", authorNickname);
            model.addAttribute("formattedDate", formattedDate);
            model.addAttribute("isLiked", isLiked);
            model.addAttribute("postLikeCount", postLikeCount);
            model.addAttribute("activeCategory", "community_free");

            if(boardId == 1){
                model.addAttribute("activePage", "community_free");
            } else if(boardId == 2){
                model.addAttribute("activePage", "community_photo");
            } else if(boardId == 3){
                model.addAttribute("activePage", "community_video");
            }

            model.addAttribute("contentFragment", "fragments/community_detail");
            model.addAttribute("currentUserId", currentUser.getId() - 1);
            model.addAttribute("commentCount", commentCount);

            // 사용자 정보를 모델에 추가
            Optional<User> currentUserOptional = userRepository.findByUsername(userDetails.getUsername());
            currentUserOptional.ifPresent(user -> model.addAttribute("user", user));
        } else {
            return "error/404";
        }
        return "index";
    }

    @GetMapping("/community_free")
    public String community_free(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_free");
        model.addAttribute("contentFragment", "fragments/community_free");
        return "index";
    }
    @GetMapping("/community_photo")
    public String community_photo(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_photo");
        model.addAttribute("contentFragment", "fragments/community_photo");
        return "index";
    }

    @GetMapping("/community_video")
    public String community_video(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_video");
        model.addAttribute("contentFragment", "fragments/community_video");
        return "index";
    }

    @GetMapping("/community_write")
    public String community_write(@RequestParam("boardId") int boardId, Model model) {
        model.addAttribute("activeCategory", "community_free");
        if(boardId == 1){
            model.addAttribute("activePage", "community_free");
        } else if(boardId == 2){
            model.addAttribute("activePage", "community_photo");
        } else if(boardId == 3){
            model.addAttribute("activePage", "community_video");
        }
        model.addAttribute("contentFragment", "fragments/community_write");
        model.addAttribute("boardId", boardId);
        return "index";
    }
}
