package com.study.trip.controller.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.trip.config.auth.PrincipalDetail;
import com.study.trip.dto.review.ReviewSaveRequestDto;
import com.study.trip.dto.review.ReviewUpdateRequestDto;
import com.study.trip.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

	private final ReviewService reviewService;

	/**
	 * 글작성 API
	 */
	@PostMapping("/api/v1/review")
	public Long save(@RequestBody ReviewSaveRequestDto reviewSaveRequestDto,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return reviewService.save(reviewSaveRequestDto, principalDetail.getUser());
	}

	/**
	 * 글삭제 API
	 */
	@DeleteMapping("/api/v1/review/{id}")
	public Long deleteById(@PathVariable Long id) {
		reviewService.deleteById(id);
		return id;
	}

	/**
	 * 글수정 API
	 */
	@PutMapping("/api/v1/review/{id}")
	public Long update(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto) {
		return reviewService.update(id, reviewUpdateRequestDto);
	}
}