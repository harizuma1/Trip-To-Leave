package com.study.trip.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.study.trip.dto.BoardDto;
import com.study.trip.service.BoardService;

@Controller
public class BoardController {
	private BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping("/")
	public String list(Model model) {
		List<BoardDto> boardDtoList = boardService.getBoardList();
		model.addAttribute("postList", boardDtoList);
		return "board/list.html";
	}

	@GetMapping("/post")
	public String post() {
		return "board/post.html";
	}

	@PostMapping("/post")
	public String write(BoardDto boardDto) {
		boardService.savePost(boardDto);
		return "redirect:/";
	}
}
