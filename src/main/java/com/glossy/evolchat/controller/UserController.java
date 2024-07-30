package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.User;
import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/current")
    public ResponseEntity<UserPoints> getUserPointsByUsername(@AuthenticationPrincipal Authentication authentication) {
        String username = authentication.getName();

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<UserPoints> userPointsOptional = userService.getUserPointsByUsername(username);

        if (userPointsOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserPoints userPoints = userPointsOptional.get();
        return ResponseEntity.ok(userPoints);
    }
}
