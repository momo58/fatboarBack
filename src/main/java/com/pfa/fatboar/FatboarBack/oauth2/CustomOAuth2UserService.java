package com.pfa.fatboar.FatboarBack.oauth2;

import com.pfa.fatboar.FatboarBack.dto.OAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Role;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    public static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
            throws OAuth2AuthenticationException {

        // A call to super() will make the REST call to fetch the userinfo from the external provider(Google)
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        //attributes.forEach((k,v) -> logger.info("Key: " + k + "Value: " + v));

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.createOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(), attributes);


        updateUser(userInfo);

        return oAuth2User;
    }

    private void updateUser(OAuth2UserInfo userInfo) {
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
