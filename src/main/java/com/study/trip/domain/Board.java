package com.study.trip.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Board {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 10, nullable = false)
	private String title;

	@Column(length = 10, nullable = false)
	private String content;

	@Column(length = 100, nullable = false)
	private String location;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime startDate;

	@LastModifiedDate
	private LocalDateTime endDate;

	@Column(length = 100, nullable = false)
	private Long recruitsNum;

	@Column(length = 100, nullable = false)
	private Long views;

	@Builder
	public Board(Long id, String title, String content, String location, LocalDateTime startDate,
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
