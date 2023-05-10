package com.study.trip.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.study.trip.domain.entity.Board;
import com.study.trip.domain.repository.BoardRepository;
import com.study.trip.dto.BoardDto;

@Service

public class BoardService {

	private BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@Transactional
	public Long savePost(BoardDto boardDto) {
		return boardRepository.save(boardDto.toEntity()).getId();
	}


	@Transactional(readOnly = true)
	public Page<Board> pageList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}



	@Transactional
	public BoardDto getPost(Long id) {
		Board board = boardRepository.findById(id).get();

		BoardDto boardDto = BoardDto.builder()
			.id(board.getId())
			.study_project(board.getStudy_project())
			.person_num(board.getPerson_num())
			.online_offline(board.getOnline_offline())
			.duration(board.getDuration())
			.skill(board.getSkill())
			.date(board.getDate())
			.calling(board.getCalling())
			.title(board.getTitle())
			.input_content(board.getInput_content())
			.createdDate(board.getCreatedDate())
			.build();
		return boardDto;
	}

	@Transactional
	public void deletePost(Long id) {
		boardRepository.deleteById(id);
	}

	/* 회원가입 시, 유효성 체크 */
	@Transactional(readOnly = true)
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();

		/* 유효성 검사에 실패한 필드 목록을 받음 */
		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		return validatorResult;
	}

  // boardDtoList 선언해서 거가에 add 해서 리턴 하면 나와야하는데 안나옴
	@Transactional
	public List<BoardDto> searchPosts(String keyword) {
		List<Board> boards = boardRepository.findByTitleContaining(keyword);
		List<BoardDto> boardDtoList = new ArrayList<>();

		if(boards.isEmpty()) return boardDtoList;

		for (Board board : boards) {
			boardDtoList.add(this.convertEntityToDto(board));
		}

		return boardDtoList;
	}

	private BoardDto convertEntityToDto(Board board) {
			return BoardDto.builder()
				.id(board.getId())
				.study_project(board.getStudy_project())
				.person_num(board.getPerson_num())
				.online_offline(board.getOnline_offline())
				.duration(board.getDuration())
				.skill(board.getSkill())
				.date(board.getDate())
				.calling(board.getCalling())
				.title(board.getTitle())
				.input_content(board.getInput_content())
				.build();

		}

}

