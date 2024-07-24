package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.Comment;
import com.glossy.evolchat.model.Post;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.CommentRepository;
import com.glossy.evolchat.repository.PostLikeRepository;
import com.glossy.evolchat.repository.PostRepository;
import com.glossy.evolchat.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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

@Controller
public class CommunityController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @GetMapping("/community")
    public String community(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community");
        return "index";
    }

    @GetMapping("/community_detail")
    public String communityDetail(@RequestParam("postId") int postId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            List<Comment> comments = commentRepository.findByPostPostId(postId);

            Optional<User> authorOptional = userRepository.findByUsername(post.getUserId());
            String authorNickname = authorOptional.map(User::getNickname).orElse("Unknown");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            String formattedDate = post.getCreatedAt().format(formatter);

            boolean isLiked = postLikeRepository.findByPostIdAndUserId(postId, userDetails.getUsername()).isPresent();
            long postLikeCount = postLikeRepository.countByPostId(postId);

            model.addAttribute("post", post);
            model.addAttribute("comments", comments != null ? comments : List.of());
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
        model.addAttribute("activePage", "community_write");
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
            @RequestParam("boardId") int boardId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 세션이 만료되었습니다.");
        }

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setBoardId(boardId);
        post.setTags(tags);
        post.setUserId(username);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        return ResponseEntity.ok(post);
    }
}
