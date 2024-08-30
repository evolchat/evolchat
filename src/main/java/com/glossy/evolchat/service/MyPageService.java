package com.glossy.evolchat.service;

import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MyPageService {

    @Autowired
    private UserRepository userRepository;

    public void saveUserWithFiles(User user, MultipartFile profilePicture, MultipartFile backgroundPicture, MultipartFile idCardPicture) throws IOException {
        if (profilePicture != null && !profilePicture.isEmpty()) {
            user.setProfilePicture(profilePicture.getBytes());
        }
        if (backgroundPicture != null && !backgroundPicture.isEmpty()) {
            user.setHomeBackgroundPicture(backgroundPicture.getBytes());
        }
        if (idCardPicture != null && !idCardPicture.isEmpty()) {
            user.setIdCardPicture(idCardPicture.getBytes());
        }

        userRepository.save(user);
    }
}
