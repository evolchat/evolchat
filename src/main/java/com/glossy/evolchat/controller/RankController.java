package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.UserDto;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RankController {

    @Autowired
    private final UserService userService;

    @GetMapping("/rank")
    public String rank(Model model) {
        model.addAttribute("activeCategory", "rank");
        model.addAttribute("activePage", "rank");
        model.addAttribute("contentFragment", "fragments/rank");
        return "index"; // HTML 뷰를 렌더링합니다
    }

    @GetMapping("/rank/rankedUsers")
    public ResponseEntity<List<UserDto>> getRankedUsers(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // 페이지 번호를 0부터 시작하도록 수정
        Page<User> userPage = userService.getRankedUsers(pageable);

        List<UserDto> userDtos = userPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(userPage.getTotalPages())); // 전체 페이지 수를 HTTP 헤더에 추가

        return new ResponseEntity<>(userDtos, headers, HttpStatus.OK);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setNickname(user.getNickname());
        userDto.setProfilePicture(user.getProfilePicture());
        userDto.setBettingWins(user.getBettingWins());
        userDto.setBettingLosses(user.getBettingLosses());
        userDto.setBettingProfit(user.getBettingProfit());
        userDto.setWinRate(calculateWinRate(user));
        userDto.setMaxWinningStreak(user.getMaxWinningStreak());
        userDto.setActiveDays(calculateActiveDays(user.getCreatedAt()));
        return userDto;
    }
    private String calculateWinRate(User user) {
        if (user.getBettingWins() + user.getBettingLosses() > 0) {
            double winRate = (double) user.getBettingWins() * 100 / (user.getBettingWins() + user.getBettingLosses());
            return String.format("%.2f%%", winRate);
        }
        return "0.00%";
    }
    private int calculateActiveDays(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        return Period.between(createdAt.toLocalDate(), now.toLocalDate()).getDays();
    }
}
