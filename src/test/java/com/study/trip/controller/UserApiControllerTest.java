package com.study.trip.controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.study.trip.domain.user.Role;
import com.study.trip.domain.user.User;
import com.study.trip.domain.user.UserRepository;
import com.study.trip.dto.user.UserSaveRequestDto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@AfterEach
	public void cleanup() throws Exception {
		userRepository.deleteAll();
	}

	@Test
	public void User_가입완료_테스트() throws Exception {
		//given
		String username = "test";
		String nickname = "babo";

		UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
			.username(username)
			.password(bCryptPasswordEncoder.encode("1234"))
			.email("test@naver.com")
			.nickname(nickname)
			.role(Role.USER)
			.build();

		String url = "http://localhost:" + port + "/auth/api/v1/user";

		//when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, userSaveRequestDto, Long.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<User> userList = userRepository.findAll();
		assertThat(userList.get(0).getUsername()).isEqualTo(username);
		assertThat(userList.get(0).getNickname()).isEqualTo(nickname);
	}
}
