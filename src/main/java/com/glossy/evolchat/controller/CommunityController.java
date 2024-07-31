package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.CommunityCommentRepository;
import com.glossy.evolchat.repository.CommunityPostLikeRepository;
import com.glossy.evolchat.repository.CommunityPostRepository;
import com.glossy.evolchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/community_detail")
    public String communityDetail(@RequestParam("postId") int postId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<CommunityPost> postOptional = communityPostRepository.findById(postId);
        if (postOptional.isPresent()) {
            CommunityPost communityPost = postOptional.get();

            // CommunityPost 객체를 전달하여 댓글 조회
            List<CommunityComment> communityComments = communityCommentRepository.findByCommunityPost(communityPost);

            Optional<User> authorOptional = userRepository.findByUsername(communityPost.getUserId());
            String authorNickname = authorOptional.map(User::getNickname).orElse("Unknown");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            String formattedDate = communityPost.getCreatedAt().format(formatter);

            boolean isLiked = communityPostLikeRepository.findByPostIdAndUserId(postId, userDetails.getUsername()).isPresent();
            long postLikeCount = communityPostLikeRepository.countByPostId(postId);

            model.addAttribute("communityPost", communityPost);
            model.addAttribute("comments", communityComments != null ? communityComments : List.of());
            model.addAttribute("authorNickname", authorNickname);
            model.addAttribute("formattedDate", formattedDate);
            model.addAttribute("isLiked", isLiked);
            model.addAttribute("postLikeCount", postLikeCount);
            model.addAttribute("activeCategory", "community_free");
            model.addAttribute("activePage", "community_detail");
            model.addAttribute("contentFragment", "fragments/community_detail");

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

    @GetMapping("/community_videos")
    public String community_videos(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_videos");
        model.addAttribute("contentFragment", "fragments/community_videos");
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
            model.addAttribute("activePage", "community_videos");
        }
        model.addAttribute("contentFragment", "fragments/community_write");
        model.addAttribute("boardId", boardId);
        return "index";
    }


    @PostMapping("/submit_post")
    @ResponseBody
    public ResponseEntity<?> submitPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tags") String tags,
            @RequestParam("boardId") int boardId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 세션이 만료되었습니다.");
        }

        CommunityPost communityPost = new CommunityPost();
        communityPost.setTitle(title);
        communityPost.setContent(content);
        communityPost.setBoardId(boardId);
        communityPost.setTags(tags);
        communityPost.setUserId(username);
        communityPost.setCreatedAt(LocalDateTime.now());
        communityPost.setUpdatedAt(LocalDateTime.now());

        communityPostRepository.save(communityPost);

        return ResponseEntity.ok(communityPost);
    }
}
