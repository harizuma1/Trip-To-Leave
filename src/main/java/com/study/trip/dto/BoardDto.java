package com.study.trip.dto;

import java.time.LocalDateTime;

import com.study.trip.domain.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
	private Long id;
	private String title;
	private String content;
	private String location;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Long recruitsNum;
	private Long views;

	public Board toEntity() {
		Board build = Board.builder()
			.id(id)
			.title(title)
			.content(content)
			.location(location)
			.startDate(startDate)
			.endDate(endDate)
			.recruitsNum(recruitsNum)
			.views(views)
			.build();
		return build;
	}

	@Builder
	public BoardDto(Long id, String title, String content, String location, LocalDateTime startDate,
		LocalDateTime endDate, Long recruitsNum, Long views) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.recruitsNum = recruitsNum;
		this.views = views;
	}
}
