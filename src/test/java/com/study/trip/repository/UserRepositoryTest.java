package com.study.trip.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.study.trip.domain.user.Role;
import com.study.trip.domain.user.User;
import com.study.trip.domain.user.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@AfterEach
	public void cleanup() {
		userRepository.deleteAll();
	}

	@Test
	public void 회원가입_테스트() {
		//given
		String username = "test";
		String nickname = "babo";

		userRepository.save(User.builder()
			.username(username)
			.password("1234")
			.email("test@naver.com")
			.nickname(nickname)
			.role(Role.USER)
			.build());

		//when
		List<User> userList = userRepository.findAll();

		//then
		User user = userList.get(0);
		assertThat(user.getUsername()).isEqualTo(username);
		assertThat(user.getNickname()).isEqualTo(nickname);
	}
}
