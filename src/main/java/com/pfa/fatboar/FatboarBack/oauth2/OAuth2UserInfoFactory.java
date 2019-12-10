package com.pfa.fatboar.FatboarBack.oauth2;

import java.util.Map;

import com.pfa.fatboar.FatboarBack.dto.FacebookOAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.dto.GoogleOAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.dto.OAuth2UserInfo;
import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;

public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo createOAuth2UserInfo (String registrationId, Map<String, Object> attributes) {
		switch (registrationId) {
		case "google" : return new GoogleOAuth2UserInfo(attributes);
		case "facebook" : return new FacebookOAuth2UserInfo(attributes);
		}
		return null;
	}

	public static void updateUser(OAuth2UserInfo userInfo, ClientRepository clientRepository) {
		Client client = clientRepository
				.findByEmail(userInfo.getEmail())
				.orElse(new Client());

		client.setEmail(userInfo.getEmail());
		client.setImageUrl(userInfo.getImageUrl());
		client.setUsername(userInfo.getName());
		client.setSub(userInfo.getId());
		clientRepository.save(client);
	}
}
