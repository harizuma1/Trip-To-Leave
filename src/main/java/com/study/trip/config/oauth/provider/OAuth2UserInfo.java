package com.study.trip.config.oauth.provider;

public interface OAuth2UserInfo {

	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
