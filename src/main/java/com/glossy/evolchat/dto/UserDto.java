package com.glossy.evolchat.dto;

import lombok.Data;

@Data
public class UserDto {
    private int userId;
    private String username;
    private String email;
    private int roleId;

    // New fields
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
}
