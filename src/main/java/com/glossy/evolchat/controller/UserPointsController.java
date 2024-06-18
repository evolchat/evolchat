package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user-points")
public class UserPointsController {

    @Autowired
    private UserPointsService userPointsService;

    @GetMapping("/{id}")
    public Optional<UserPoints> getUserPointsById(@PathVariable int id) {
        return userPointsService.getUserPointsById(id);
    }

    @PostMapping
    public UserPoints createUserPoints(@RequestBody UserPoints userPoints) {
        return userPointsService.saveUserPoints(userPoints);
    }
}
