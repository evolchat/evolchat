package com.glossy.evolchat.dto;

public class FriendRequestDTO {
    private Long id;
    private String senderNickname;
    private String receiverNickname;
    private String message;
    private boolean isSentByCurrentUser;

    // 기본 생성자
    public FriendRequestDTO() {}

    // 생성자
    public FriendRequestDTO(Long id, String senderNickname, String receiverNickname, String message, boolean isSentByCurrentUser) {
        this.id = id;
        this.senderNickname = senderNickname;
        this.receiverNickname = receiverNickname;
        this.message = message;
        this.isSentByCurrentUser = isSentByCurrentUser;
    }

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSentByCurrentUser() {
        return isSentByCurrentUser;
    }

    public void setSentByCurrentUser(boolean sentByCurrentUser) {
        isSentByCurrentUser = sentByCurrentUser;
    }
}
