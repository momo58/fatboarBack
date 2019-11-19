package com.pfa.fatboar.FatboarBack.oauth2;

import com.pfa.fatboar.FatboarBack.dto.FacebookOAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.dto.GoogleOAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.dto.OAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.models.Role;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo createOAuth2UserInfo (String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google" : return new GoogleOAuth2UserInfo(attributes);
            case "facebook" : return new FacebookOAuth2UserInfo(attributes);
        }
        return null;
    }

    public static void updateUser(OAuth2UserInfo userInfo, UserRepository userRepository) {
        Optional<User> userOpt = userRepository.findByEmail(userInfo.getEmail());
        User user = null;

        if (userOpt.isPresent()) {
            user = userOpt.get();
        }

        if(user == null) {
            user = new User();
        }

        user.setEmail(userInfo.getEmail());
        user.setImageUrl(userInfo.getImageUrl());
        user.setUsername(userInfo.getName());
        user.setSub(userInfo.getId());
        user.setRole(Role.ROLE_CLIENT);
        userRepository.save(user);
    }
}
