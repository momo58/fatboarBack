package com.pfa.fatboar.FatboarBack.oauth2;

import com.pfa.fatboar.FatboarBack.dto.FacebookOAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.dto.GoogleOAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.dto.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo createOAuth2UserInfo (String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google" : return new GoogleOAuth2UserInfo(attributes);
            case "facebook" : return new FacebookOAuth2UserInfo(attributes);
        }
        return null;
    }
}
