package com.study.trip.controller.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.trip.config.auth.PrincipalDetail;
import com.study.trip.domain.reviewReply.ReviewReply;
import com.study.trip.service.ReviewReplyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewReplyApiController {

	private final ReviewReplyService reviewReplyService;

	@PostMapping("/api/v1/review/{reviewId}/reviewReply")
	public void reviewSave(@PathVariable Long reviewId,
		@RequestBody ReviewReply reviewReply,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		reviewReplyService.reviewReplySave(reviewId, reviewReply, principalDetail.getUser());
	}

	@DeleteMapping("/api/v1/review/{reviewId}/reviewReply/{reviewReplyId}")
	public void reviewDelete(@PathVariable Long reviewReplyId) {
		reviewReplyService.reviewReplyDelete(reviewReplyId);
	}
}
