package com.glossy.evolchat.dto;

import com.glossy.evolchat.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FriendRequestDTO {
    private Long id;
    private String nickname;
    private String message;

    // Constructor
    public FriendRequestDTO(Long id, String nickname, String message) {
        this.id = id;
        this.nickname = nickname;
        this.message = message;
    }
}
