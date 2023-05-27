package com.study.trip.config.oauth.provider;

import java.util.Map;

import com.study.trip.controller.UserController;
import com.study.trip.domain.user.KakaoProfile;

import lombok.Setter;

@Setter
public class KakaoUserInfo implements OAuth2UserInfo {

	private Map<String, Object> attributes;

	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return (String)attributes.get("id");
	}

	@Override
	public String getProvider() {
		return attributes.get("Provider").toString();
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}
}
