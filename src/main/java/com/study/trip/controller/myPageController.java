package com.study.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.trip.config.auth.PrincipalDetail;
import com.study.trip.domain.board.Board;
import com.study.trip.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class myPageController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/user/mypage/mypost")
	public String mypost(Model model,
		@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		Page<Board> boards = boardService.findByUser_Id(principalDetail.getId(), pageable);
		int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
		int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("boards", boards);
		return "layout/user/myPage/myPage-myPost";
	}
}
