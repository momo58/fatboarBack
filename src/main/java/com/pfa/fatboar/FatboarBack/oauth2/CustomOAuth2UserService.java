package com.pfa.fatboar.FatboarBack.oauth2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.pfa.fatboar.FatboarBack.dto.OAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	public static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

	@Autowired
	private ClientRepository ClientRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
			throws OAuth2AuthenticationException {

		// A call to super() will make the REST call to fetch the userinfo from the external provider(Google)
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		Map<String, Object> attributes = oAuth2User.getAttributes();
		//attributes.forEach((k,v) -> logger.info("Key: " + k + "Value: " + v));

		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.createOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), attributes);


		OAuth2UserInfoFactory.updateUser(userInfo, ClientRepository);

		return oAuth2User;
	}


}
