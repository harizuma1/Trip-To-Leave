package com.study.trip.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.trip.config.auth.PrincipalDetail;
import com.study.trip.domain.user.KakaoProfile;
import com.study.trip.domain.user.OAuthToken;
import com.study.trip.domain.user.Role;
import com.study.trip.domain.user.User;
import com.study.trip.domain.user.UserRepository;
import com.study.trip.service.UserService;

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;



	/**
	 * 회원가입 페이지
	 */
	@GetMapping("/auth/user/save")
	public String userSave() {
		return "layout/user/user-save";
	}

	/**
	 * 로그인 페이지
	 */
	@GetMapping("/auth/user/login")
	public String userLogin() {
		return "layout/user/user-login";
	}

	/**
	 * 회원수정 페이지
	 */
	@GetMapping("/user/update")
	public String userUpdate(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
		model.addAttribute("principal", principalDetail.getUser());
		return "layout/user/user-update";
	}

	@GetMapping("auth/kakao/login")
	public String moveKakao(){
		return "layout/user/kakao-login";
	}




	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

		// POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
		// 이 때 필요한 라이브러리가 RestTemplate, 얘를 쓰면 http 요청을 편하게 할 수 있다.
		RestTemplate rt = new RestTemplate();

		// HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		// body 데이터를 담을 오브젝트인 MultiValueMap를 만들어보자
		// body는 보통 key, value의 쌍으로 이루어지기 때문에 자바에서 제공해주는 MultiValueMap 타입을 사용한다.
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "b3d2869f03813040a38362cd889fa316");
		params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
		params.add("code", code);

		// 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
		// kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
		ResponseEntity<String> response = rt.exchange(
			"https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
			HttpMethod.POST, // 요청할 방식
			kakaoTokenRequest, // 요청할 때 보낼 데이터
			String.class // 요청 시 반환되는 데이터 타입
		);

		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try{
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e){
			e.printStackTrace();
		}

		System.out.println("카카오 엑세스 토큰 : " + oAuthToken.getAccess_token());


		RestTemplate rt2 = new RestTemplate();

		// HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer " + oAuthToken.getAccess_token());
		headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");



		// 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
		// kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

		// POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
		ResponseEntity<String> response2 = rt2.exchange(
			"https://kapi.kakao.com/v2/user/me", // https://{요청할 서버 주소}
			HttpMethod.POST, // 요청할 방식
			kakaoProfileRequest2, // 요청할 때 보낼 데이터
			String.class // 요청 시 반환되는 데이터 타입
		);

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try{
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e){
			e.printStackTrace();
		}
		System.out.println(response2.getBody());

		System.out.println("카카오 아이디 번호 : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("동행 웹 유저 네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("동행 앱 유저 이메일 : " + kakaoProfile.getKakao_account().getEmail());

		UUID garbagePassword = UUID.randomUUID();
		System.out.println("동행 앱 유저 패스워드 " + cosKey);

		User kakaoUser = User.builder()
			.username("kakao_" + kakaoProfile.getId())
			.password(garbagePassword.toString())
			.email(kakaoProfile.getKakao_account().getEmail())
			.nickname("소셜로그인")
			.role(Role.USER)
			.provider("kakao")
			.providerId(kakaoProfile.getId().toString())
			.build();


		User originUser = userService.findUser(kakaoUser.getUsername());

		if(originUser.getUsername() == null){
			userService.save(kakaoUser);
		}
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("username", "kakao" + "_" + kakaoProfile.getId());
		attributes.put("email", kakaoProfile.getKakao_account().getEmail());
		attributes.put("provider", "kakao");
		attributes.put("providerId", kakaoProfile.getId().toString());




		return "buta";





	}

	@GetMapping("/user/mypage")
	public String myPage() {return "layout/user/myPage/myPage-main";}


}
