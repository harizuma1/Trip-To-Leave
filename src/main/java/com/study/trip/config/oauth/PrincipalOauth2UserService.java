package com.study.trip.config.oauth;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.study.trip.config.auth.PrincipalDetail;
import com.study.trip.domain.user.Role;
import com.study.trip.domain.user.User;
import com.study.trip.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId(); //google
		String providerId = oAuth2User.getAttribute("sub");
		String username = provider + "_" + providerId;
		String password = UUID.randomUUID().toString();
		String email = oAuth2User.getAttribute("email");
		String nickname = "소셜 로그인";
		Role role = Role.USER;

		Optional<User> userEntity = userRepository.findByUsername(username);

		User user;
		if (userEntity.isPresent()) {
			user = userEntity.get();
		} else {
			user = User.builder()
				.username(username)
				.password(password)
				.email(email)
				.nickname(nickname)
				.role(role)
				.provider(provider)
				.providerId(providerId)
				.build();

			userRepository.save(user);

		}

		return new PrincipalDetail(user, oAuth2User.getAttributes());
	}
}