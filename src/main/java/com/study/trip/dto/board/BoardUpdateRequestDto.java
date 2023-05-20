package com.study.trip.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardUpdateRequestDto {

	private String title;
	private String content;
	private String startday;
	private String lastday;
	private int pnum;
}
