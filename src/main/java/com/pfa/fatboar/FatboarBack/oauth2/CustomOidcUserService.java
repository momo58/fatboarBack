package com.pfa.fatboar.FatboarBack.oauth2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.pfa.fatboar.FatboarBack.dto.OAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;


@Service
public class CustomOidcUserService extends OidcUserService {

    public static final Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);

        Map<String, Object> attributes = oidcUser.getAttributes();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.createOAuth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(), attributes);


        OAuth2UserInfoFactory.updateUser(userInfo, clientRepository);

        return oidcUser;
    }
}
