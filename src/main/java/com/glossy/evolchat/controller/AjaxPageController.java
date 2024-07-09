package com.glossy.evolchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AjaxPageController {

    //  에볼루션
    @GetMapping("/home_page")
    public String home_page() {
        return "home";
    }

    //  오픈채팅
    @GetMapping("/openchat_page")
    public String openchat_page() {
        return "openchat";
    }

    //  커뮤니티
    @GetMapping("/community_page")
    public String community_page() {
        return "community";
    }

    @GetMapping("/community_detail_page")
    public String community_detail_page() {
        return "community_detail";
    }

    @GetMapping("/community_free_page")
    public String community_free_page() {
        return "community_free";
    }

    @GetMapping("/community_photo_page")
    public String community_photo_page() {
        return "community_photo";
    }

    @GetMapping("/community_write_page")
    public String community_write_page() {
        return "community_write";
    }

    //  랭킹
    @GetMapping("/rank_page")
    public String rank_page() {
        return "rank";
    }

    //  상점
    @GetMapping("/shopping_page")
    public String shopping_page() {
        return "shopping";
    }

    //  고객센터
    @GetMapping("/support_help_page")
    public String support_help_page() {
        return "support_help";
    }

    @GetMapping("/support_inquiry_page")
    public String support_inquiry_page() {
        return "support_inquiry";
    }

    @GetMapping("/support_inquiry_null_page")
    public String support_inquiry_null_page() {
        return "support_inquiry_null";
    }

    @GetMapping("/support_inquiry_write_page")
    public String support_inquiry_write_page() {
        return "support_inquiry_write";
    }

    @GetMapping("/support_notice_page")
    public String support_notice_page() {
        return "support_notice";
    }


    //  메신저

    @GetMapping("/messenger_add_friend_page")
    public String messenger_add_friend() {
        return "messenger_add_friend";
    }

    @GetMapping("/messenger_add_friend_wait_page")
    public String messenger_add_friend_wait_page() {
        return "messenger_add_friend_wait";
    }

    @GetMapping("/messenger_blocklists_page")
    public String messenger_blocklists_page() {
        return "messenger_blocklists";
    }

    @GetMapping("/messenger_friend_page")
    public String messenger_friend_page() {
        return "messenger_friend";
    }

    @GetMapping("/messenger_message_page")
    public String messenger_message_page() {
        return "messenger_message";
    }

    // 마이페이지

    @GetMapping("/mypqge_page")
    public String my_page_page() {
        return "mypqge";
    }

    @GetMapping("/my_myitem_page")
    public String my_myitem_page() {
        return "my_myitem";
    }

    @GetMapping("/my_mycash_page")
    public String my_mycash_page() {
        return "my_mycash";
    }
    @GetMapping("/my_activitypoints_page")
    public String my_activitypoints_page() {
        return "my_activitypoints";
    }
    @GetMapping("/my_goldchip_page")
    public String my_goldchip_page() {
        return "my_goldchip";
    }
    @GetMapping("/my_mypqge_page")
    public String my_mypqge_page() {
        return "my_mypqge";
    }

}