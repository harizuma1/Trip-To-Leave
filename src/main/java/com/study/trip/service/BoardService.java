package com.study.trip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.study.trip.domain.Board;
import com.study.trip.dto.BoardDto;
import com.study.trip.repository.BoardRepository;

import jakarta.transaction.Transactional;

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

	@Transactional
	public List<BoardDto> getBoardList() {
		List<Board> boardList = boardRepository.findAll();
		List<BoardDto> boardDtoList = new ArrayList<>();

		for(Board board : boardList) {
			BoardDto boardDto = BoardDto.builder()
				.id(board.getId())
				.title(board.getTitle())
				.content(board.getContent())
				.location(board.getLocation())
				.startDate(board.getStartDate())
				.endDate(board.getEndDate())
				.recruitsNum(board.getRecruitsNum())
				.views(board.getViews())
				.build();
			boardDtoList.add(boardDto);
		}
		return boardDtoList;
	}
}
