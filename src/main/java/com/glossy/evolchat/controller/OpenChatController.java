package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenChatController {

//    private final PostService postService;
//    private final PostLikeService postLikeService;

//    @GetMapping
//    public ResponseEntity<List<PostDto>> getPosts(@RequestParam(defaultValue = "1") int page,
//                                                  @RequestParam(defaultValue = "20") int size,
//                                                  @RequestParam(required = false) Integer boardId) {
//        Pageable pageable = PageRequest.of(page - 1, size); // 페이지 번호를 0부터 시작하도록 수정
//        Page<Post> postsPage;
//
//        if (boardId != null) {
//            postsPage = postService.getPostsByBoardId(boardId, pageable);
//        } else {
//            postsPage = postService.getAllPosts(pageable);
//        }
//
//        List<PostDto> postDtos = postsPage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Pages", String.valueOf(postsPage.getTotalPages())); // 전체 페이지 수를 HTTP 헤더에 추가
//
//        return new ResponseEntity<>(postDtos, headers, HttpStatus.OK);
//    }

    @GetMapping("/openchat")
    public String openchat(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat");
        model.addAttribute("contentFragment", "fragments/openchat");
        return "index";
    }
    @GetMapping("/openchat_evolution")
    public String openchat_evolution(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_evolution");
        model.addAttribute("contentFragment", "fragments/openchat_evolution");
        return "index";
    }
    @GetMapping("/openchat_sports")
    public String openchat_sports(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_sports");
        model.addAttribute("contentFragment", "fragments/openchat_sports");
        return "index";
    }
    @GetMapping("/openchat_game")
    public String openchat_game(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_game");
        model.addAttribute("contentFragment", "fragments/openchat_game");
        return "index";
    }
    @GetMapping("/openchat_cryptocurrency")
    public String openchat_cryptocurrency(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_cryptocurrency");
        model.addAttribute("contentFragment", "fragments/openchat_cryptocurrency");
        return "index";
    }
    @GetMapping("/openchat_stock")
    public String openchat_stock(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_stock");
        model.addAttribute("contentFragment", "fragments/openchat_stock");
        return "index";
    }
    @GetMapping("/openchat_amity")
    public String openchat_amity(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_amity");
        model.addAttribute("contentFragment", "fragments/openchat_amity");
        return "index";
    }
    @GetMapping("/openchat_favorites")
    public String openchat_favorites(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_favorites");
        model.addAttribute("contentFragment", "fragments/openchat_favorites");
        return "index";
    }
    @GetMapping("/openchat_place")
    public String openchat_place(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_place");
        model.addAttribute("contentFragment", "fragments/openchat_place");
        return "index";
    }
    @GetMapping("/openchat_create")
    public String openchat_create(Model model) {
        model.addAttribute("activeCategory", "openchat");
        model.addAttribute("activePage", "openchat_create");
        model.addAttribute("contentFragment", "fragments/openchat_create");
        return "index";
    }
}
