package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.glossy.evolchat.repository.PostRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class CommunityController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/community")
    public String community(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community");
        return "index";
    }

    @GetMapping("/community_detail")
    public String community_detail(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_detail");
        model.addAttribute("contentFragment", "fragments/community_detail");
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

    @GetMapping("/community_photo_detail")
    public String community_photo_detail(Model model) {
        model.addAttribute("activeCategory", "community_free");
        model.addAttribute("activePage", "community_photo_detail");
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
    public String submitPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tags") String tags,
            @RequestParam("boardId") int boardId,
            Model model) {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setBoardId(boardId);
        post.setUserId(1);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        return "redirect:/community_free?boardId=" + boardId;
    }
}
