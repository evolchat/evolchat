package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.service.UserPointsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user-points")
public class UserPointsController {

    @Autowired
    private UserPointsService userPointsService;

    @GetMapping("/current")
    public ResponseEntity<UserPoints> getUserPointsByUsername(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<UserPoints> userPointsOptional = userPointsService.getUserPointsByUsername(id);


        if (userPointsOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserPoints userPoints = userPointsOptional.get();
        return ResponseEntity.ok(userPoints);
    }

    @PostMapping
    public UserPoints createUserPoints(@RequestBody UserPoints userPoints) {
        return userPointsService.saveUserPoints(userPoints);
    }
}
