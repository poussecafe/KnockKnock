package com.knockknock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.knockknock.dto.event.ReplyDTO;
import com.knockknock.mapper.ReplyMapper;

@Controller
public class ReplyController {

	@Autowired
	ReplyMapper reMapper;


	// 모임 댓글 목록
	@PostMapping("/meetingReplyList")
	@ResponseBody
	public List<ReplyDTO> meetingReplyList(Model model, @RequestBody String writingNumber1) {
		int writingNumber = Integer.parseInt(writingNumber1);
		return reMapper.meetingReplyList(writingNumber);
	}

	// 모임 댓글 작성
	@PostMapping("/meetingReplyInsert")
	@ResponseBody
	private int meetingReplyInsert(@RequestBody ReplyDTO replyDTO, Authentication authentication) {	 
		// 현재 로그인 사용자 정보에 접근
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();

		return reMapper.meetingReplyInsert(replyDTO, email);
	}
	
	// 모임 댓글 수정
	@RequestMapping("/meetingReplyUpdate")
	@ResponseBody
	private int meetingReplyUpdate(@RequestBody ReplyDTO replyDTO) {
		return reMapper.meetingReplyUpdate(replyDTO);
	}

	// 모임 댓글 삭제
	@RequestMapping("/meetingReplyDelete{replyNumber}")
	@ResponseBody
	private int meetingReplyDelete(@PathVariable("replyNumber") int replyNumber) {
		return reMapper.meetingReplyDelete(replyNumber);
	}
	
	
	// 이벤트 댓글 목록
	@PostMapping("/eventReplyList")
	@ResponseBody
	public List<ReplyDTO> eventReplyList(Model model, @RequestBody String writingNumber1) {
		int writingNumber = Integer.parseInt(writingNumber1);
		return reMapper.eventReplyList(writingNumber);
	}

	// 이벤트 댓글 작성
	@PostMapping("/eventReplyInsert")
	@ResponseBody
	private int eventReplyInsert(@RequestBody ReplyDTO replyDTO, Authentication authentication) {
		// 현재 로그인 사용자 정보에 접근
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();

		return reMapper.eventReplyInsert(replyDTO, email);
	}
	
	// 이벤트 댓글 수정
	@RequestMapping("/eventReplyUpdate")
	@ResponseBody
	private int eventReplyUpdate(@RequestBody ReplyDTO replyDTO) {
		return reMapper.eventReplyUpdate(replyDTO);
	}

	// 이벤트 댓글 삭제
	@RequestMapping("/eventReplyDelete{replyNumber}")
	@ResponseBody
	private int eventReplyDelete(@PathVariable("replyNumber") int replyNumber) {
		return reMapper.eventReplyDelete(replyNumber);
	}
	
}