package com.study.trip.controller;

import java.util.List;
import java.util.Map;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.trip.domain.entity.Board;
import com.study.trip.domain.repository.BoardRepository;
import com.study.trip.dto.BoardDto;
import com.study.trip.service.BoardService;

import jakarta.validation.Valid;

@Controller
public class BoardController {

	private BoardRepository boardRepository;
	private BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping("/")
	public String list(Model model, @PageableDefault(size = 2, sort = "createdDate", direction = Sort.Direction.ASC)
	Pageable pageable) {

		Page<Board> postList = boardService.pageList(pageable);
		int startPage = Math.max(1, postList.getPageable().getPageNumber() - 4);
		int endPage = Math.min(postList.getTotalPages(), postList.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("postList", postList);
		return "board/list";
	}

	@GetMapping("/post")
	public String post() {
		return "board/post";
	}

	@PostMapping("/post")
	public String write(@Valid BoardDto boardDto, Errors errors, Model model) {

		if (errors.hasErrors()) {
			// 글 작성 실패시 입력 데이터 값을 유지
			model.addAttribute("boardDto", boardDto);

			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = boardService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}

			// 글 작성 페이지로 다시 리턴
			return "board/post";
		}

		boardService.savePost(boardDto);
		return "redirect:/";
	}

	@GetMapping("/post/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		BoardDto boardDto = boardService.getPost(id);
		model.addAttribute("post", boardDto);
		return "board/detail";
	}

	@GetMapping("/post/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		BoardDto boardDto = boardService.getPost(id);
		model.addAttribute("post", boardDto);
		return "board/edit";
	}

	@PutMapping("/post/edit/{id}")
	public String update(BoardDto boardDto) {
		boardService.savePost(boardDto);
		return "redirect:/";
	}

	@DeleteMapping("/post/{id}")
	public String delete(@PathVariable("id") Long id) {
		boardService.deletePost(id);
		return "redirect:/";
	}
// @RequestParam(value = "keyword")을 통해 검색 창에 입력한 keyword를 받고 모델에 에드 한거
	@PostMapping("/")
	public String search(@RequestParam(value = "keyword") String keyword, Model model) {
		List<BoardDto> boardList = boardService.searchPosts(keyword);
		model.addAttribute("boardList", boardList);
		return "board/list";
	}

}
