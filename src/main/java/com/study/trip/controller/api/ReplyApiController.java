package com.study.trip.controller.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.trip.config.auth.PrincipalDetail;
import com.study.trip.domain.reply.Reply;
import com.study.trip.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReplyApiController {

	private final ReplyService replyService;

	@PostMapping("/api/v1/board/{boardId}/reply")
	public void save(@PathVariable Long boardId,
		@RequestBody Reply reply,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		replyService.replySave(boardId, reply, principalDetail.getUser());
	}

	@DeleteMapping("/api/v1/board/{boardId}/reply/{replyId}")
	public void delete(@PathVariable Long replyId) {
		replyService.replyDelete(replyId);
	}
}
