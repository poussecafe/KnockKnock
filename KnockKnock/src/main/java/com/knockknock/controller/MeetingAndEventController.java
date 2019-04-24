package com.knockknock.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.knockknock.dto.event.Criteria;
import com.knockknock.dto.event.EventVDTO;
import com.knockknock.dto.event.MeetingVDTO;
import com.knockknock.dto.event.PageMaker;
import com.knockknock.dto.member.MemberDTO;
import com.knockknock.mapper.MeetingAndEventMapper;
import com.knockknock.security.MemberController;
import com.knockknock.service.MeetingAndEventService;

import net.sf.json.JSONArray;

@Controller
public class MeetingAndEventController {
	@Autowired
	MeetingAndEventMapper meMapper;
	
	@Autowired
	MeetingAndEventService meService;

	//from 성현 : 로그인시 상단 정보표시 관련(신경안쓰셔도됨)
	@Autowired
	MemberController mc;
	
	@RequestMapping("/meetingList") //미팅리스트
	private String meetingList(@ModelAttribute("cri") Criteria cri, Model model){		
		model.addAttribute("MeetingList", meMapper.meetingList(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(meMapper.meetingCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);  // 게시판 하단의 페이징 관련, 이전페이지, 페이지 링크 , 다음 페이지
		return "event/MeetingList";
	}
	
	@SuppressWarnings("static-access") //경고무시
	@RequestMapping("/meetingView") //미팅 상세보기
	private String meetingView(@RequestParam("writingNumber") int writingNumber, Model model){
		List<MeetingVDTO> pro = new ArrayList<MeetingVDTO>();
		pro.addAll(meMapper.patyMemberNum(writingNumber));
		JSONArray jsonArray = new JSONArray();
		model.addAttribute("patyMemberNum", jsonArray.fromObject(pro));
//		System.err.println(jsonArray.fromObject(pro));
		meMapper.meetingViewHit(writingNumber);
		model.addAttribute("MeetingView", meMapper.meetingView(writingNumber));
		return "event/MeetingView";
	}
	
	@RequestMapping("/writeBoardForm") //미팅 글 쓰기
	private String writeBoardForm(Authentication authentication, HttpSession hs, MemberDTO memberDTO){
		//from 성현 : 로그인시 상단 정보표시 관련(신경안쓰셔도됨)
		mc.getSession(authentication,hs,memberDTO);

		return "event/WriteBoard";
	}
	
	@RequestMapping("/writeBoard") //미팅 글 쓰기
	private String writeBoard(MeetingVDTO meetingVDTO){
		int writingNumber = meetingVDTO.getWritingNumber();
		meMapper.meetingInsert(meetingVDTO);
		meService.meetingImageUpload(writingNumber, meetingVDTO);
		
		return "redirect:/meetingList";
	}

	@RequestMapping("/meetingModifyForm")
	private String meetingModifyForm(Model model, @RequestParam("writingNumber") int writingNumber){
		model.addAttribute("meetingModifyForm", meMapper.meetingModifyForm(writingNumber));
		return "event/meetingModify";
	}
	
	@PostMapping("/meetingModify")
	private String meetingModify(MeetingVDTO meetingVDTO){
		int writingNumber = meetingVDTO.getWritingNumber();
		meMapper.meetingModify(meetingVDTO);
		meService.meetingImageUpload(writingNumber, meetingVDTO);
		return "redirect:/meetingList";
	}
	
	@RequestMapping("/meetingDelete") //미팅글 취소
	private String meetingCancel(@RequestParam("writingNumber") int writingNumber) {
		meMapper.meetingDelete(writingNumber);
		return "redirect:/meetingList";
	}
	
	@RequestMapping("/eventList") //이벤트 리스트
	private String eventList(@ModelAttribute("cri") Criteria cri, Model model){
		model.addAttribute("EventList", meMapper.eventList(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(meMapper.eventCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
		return "event/EventList";
	}
	
	@RequestMapping("/eventView") //미팅 상세보기
	private String eventView(@RequestParam("writingNumber") int writingNumber, Model model){
		model.addAttribute("getEMemberNum", meMapper.getEMemberNum());
		meMapper.eventViewHit(writingNumber);
		model.addAttribute("EventView", meMapper.eventView(writingNumber));

		return "event/EventView";
	}
	
	@RequestMapping(value="/mparticipate", method= RequestMethod.POST) //모임 참가하기
	@ResponseBody
	private void mparticipate(@RequestBody MeetingVDTO meetingVDTO, Authentication authentication){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();
		meMapper.mparticipate(meetingVDTO, email);		
		meMapper.mpartNumUp(meetingVDTO.getWritingNumber()); //모임인원 갱신
	}	
	
	@RequestMapping(value="/eparticipate", method= RequestMethod.POST) //이벤트 참가하기
	@ResponseBody
	private void eparticipate(@RequestBody EventVDTO eventVDTO, Authentication authentication){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();
		meMapper.eparticipate(eventVDTO, email);
		meMapper.epartNumUp(eventVDTO.getWritingNumber()); //모임인원 갱신
	}
}