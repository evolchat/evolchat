package com.glossy.evolchat.dto;

import com.glossy.evolchat.model.User;
import lombok.Data;

@Data
public class UserDto {
    private int userId;
    private String username;
    private String email;
    private int roleId;
    private String profilePicture;
    private String homeBackgroundPicture;
    private String myHomeUrl;
    private String nickname;
    private String todaysMessage;
    private String country;
    private String region;
    private String gender;
    private boolean isLocationPublic;
    private String bankName;
    private String accountNumber;
    private String idCardPicture;
    private String interests;

    // User 객체를 UserDto로 변환하는 정적 메서드
    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoleId(user.getRoleId());
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
}
