package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user-points")
public class UserPointsController {

    @Autowired
    private UserPointsService userPointsService;

    @GetMapping
    public List<UserPoints> getAllUserPoints() {
        return userPointsService.getAllUserPoints();
    }

    @GetMapping("/{id}")
    public UserPoints getUserPointsById(@PathVariable int id) {
        return userPointsService.getUserPointsById(id);
    }

    @PostMapping
    public UserPoints createUserPoints(@RequestBody UserPoints userPoints) {
        return userPointsService.saveUserPoints(userPoints);
    }

    @DeleteMapping("/{id}")
    public void deleteUserPoints(@PathVariable int id) {
        userPointsService.deleteUserPoints(id);
    }
}
