package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.UserDto;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserDto getUserById(@PathVariable int id) {
        // Assume UserDto is a DTO representing user details including new fields
        User user = userService.getUserById(id);
        UserDto userDto = new UserDto();
        // Mapping existing fields
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoleId(user.getRoleId());
        // Mapping new fields
        userDto.setProfilePicture(user.getProfilePicture());
        userDto.setHomeBackgroundPicture(user.getHomeBackgroundPicture());
        userDto.setMyHomeUrl(user.getMyHomeUrl());
        userDto.setNickname(user.getNickname());
        userDto.setTodaysMessage(user.getTodaysMessage());
        userDto.setCountry(user.getCountry());
        userDto.setRegion(user.getRegion());
        userDto.setGender(user.getGender());
        userDto.setLocationPublic(user.isLocationPublic());
        userDto.setBankName(user.getBankName());
        userDto.setAccountNumber(user.getAccountNumber());
        userDto.setIdCardPicture(user.getIdCardPicture());
        userDto.setInterests(user.getInterests());
        return userDto;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
