package com.study.trip.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.trip.config.auth.PrincipalDetail;

@Controller
public class UserController {

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

}
