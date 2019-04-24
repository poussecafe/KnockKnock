package com.knockknock.controller;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.knockknock.dto.branch.AddRoomDTO;
import com.knockknock.dto.branch.BranchDTO;
import com.knockknock.dto.branch.RoomDTO;
import com.knockknock.dto.event.EventDTO;
import com.knockknock.dto.member.ContractDTO;
import com.knockknock.service.AdminService;


@Controller
public class AdminController {

	@Autowired
	public AdminService adminService;

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	
	// event

	// 이벤트 리스트 페이지
	@RequestMapping("adminEventListView")
	public String eventListView(Model model) {
		model.addAttribute("eventListView", adminService.eventList());
		return "admin/AdminEventList";
	}

	// 이벤트 글 보기
	@RequestMapping("adminEventView")
	public String eventView(Model model, @RequestParam("writingNumber") int writingNumber) {
		model.addAttribute("eventView", adminService.eventView(writingNumber));
		model.addAttribute("joinMember", adminService.getEventJoinMember(writingNumber));
		model.addAttribute("imgPath", adminService.eventImagePath(writingNumber));
		return "admin/AdminEventPost";
	}

	// 이벤트 작성 페이지
	@RequestMapping("adminEventWriteView")
	public String eventWriteView(Model model) {
		return "admin/AdminEventWrite";
	}

	// 이벤트 등록
	@RequestMapping("adminEventWrite")
	public String eventWrite(Model model, EventDTO eventDTO, Authentication authentication) {
		adminService.eventWrite(eventDTO.getTitle(), eventDTO.getContent(), eventDTO.getEventStartTime(),
				eventDTO.getEventEndTime(), eventDTO.getAcceptStartTime(), eventDTO.getAcceptEndTime(),
				eventDTO.getRecruitMaxNumber(), eventDTO.getEventImage(), authentication);

//		model.addAttribute("eventView", adminService.eventView(eventDTO.getWritingNumber()));
//		model.addAttribute("joinMember", adminService.getEventJoinMember(eventDTO.getWritingNumber()));
//		model.addAttribute("imgPath", adminService.eventImagePath(eventDTO.getWritingNumber()));
		return "redirect:adminEventListView";
	}

	// 이벤트 수정 페이지
	@RequestMapping("adminEventModifyView")
	public String eventModifyView(Model model, @RequestParam("writingNumber") int writingNumber) {
		model.addAttribute("eventModifyView", adminService.eventModifyView(writingNumber));
		
		return "admin/AdminEventModify";
	}

	// 이벤트 수정
	@RequestMapping("adminEventModify")
	public String eventModify(Model model, EventDTO eventDTO, Authentication authentication) {
		adminService.eventModify(eventDTO.getWritingNumber(), eventDTO.getTitle(), eventDTO.getContent(), eventDTO.getEventStartTime(),
				eventDTO.getEventEndTime(), eventDTO.getAcceptStartTime(), eventDTO.getAcceptEndTime(),
				eventDTO.getRecruitMaxNumber(), eventDTO.getEventImage(), authentication);
		model.addAttribute("eventView", adminService.eventView(eventDTO.getWritingNumber()));
		model.addAttribute("joinMember", adminService.getEventJoinMember(eventDTO.getWritingNumber()));
		model.addAttribute("imgPath", adminService.eventImagePath(eventDTO.getWritingNumber()));
		return "admin/AdminEventPost";
	}

	// 이벤트 취소
	@RequestMapping("adminEventCancel")
	public String eventCancel(Model model, @RequestParam("writingNumber") int writingNumber,
			@RequestParam("cancelReason") String cancelReason) {
		adminService.eventCancel(writingNumber, cancelReason);
		return "redirect:adminEventListView";
	}

	// 이벤트 삭제
	@RequestMapping("adminEventDelete")
	public String eventDelete(Model model, @RequestParam("writingNumber") int writingNumber) {
		adminService.eventJoinDelete(writingNumber);
		adminService.eventDelete(writingNumber);
		return "redirect:adminEventListView";
	}

	// member

	//회원 검색 페이지
	@RequestMapping("adminMemberSearchView")
	public String memberSearchView(Model model) {
		return "admin/AdminMemberSearch";
	}

	//회원 검색 기능
	@RequestMapping("adminMemberSearch")
	public String memberSearch(Model model, @RequestParam("keyword") String keyword) {
		model.addAttribute("memberListView", adminService.memberListView(keyword));
		return "admin/AdminMemberSearch";
	}

	//회원 정보 페이지
	@RequestMapping("adminMemberView")
	public String memberView(Model model, @RequestParam("memberNumber") int memberNumber) {
		model.addAttribute("memberView", adminService.memberView(memberNumber));
		return "admin/AdminMemberInfo";
	}

	//계약 정보 등록 페이지
	@RequestMapping("adminContractRegistView")
	public String contractRegistView(Model model, @RequestParam("memberNumber") int memberNumber) {
		model.addAttribute("memberNumber", memberNumber);
		return "admin/AdminContractRegist";
	}

	//회원 계약 등록 페이지
	@RequestMapping("adminContractRegist")
	public String contractRegist(Model model, @RequestParam("memberNumber") int memberNumber,
			@RequestParam("branchNumber") int branchNumber, @RequestParam("roomNumber") int roomNumber,
			@RequestParam("period") int period, @RequestParam("isPet") String isPet,
			@RequestParam("emergencyNumber") String emergencyNumber, @RequestParam("bankName") String bankName,
			@RequestParam("depositor") String depositor, @RequestParam("memberAccount") String memberAccount,
			@RequestParam("contractDate") Date contractDate, @RequestParam("idNumber") String idNumber,
			@RequestParam("memo") String memo) {
		adminService.contractRegist(memberNumber, branchNumber, roomNumber, period, isPet, emergencyNumber, bankName,
				depositor, memberAccount, contractDate, idNumber, memo);
		adminService.setReturnAmount(adminService.getContractNumber(),
				adminService.getDeposit(roomNumber, branchNumber));
		model.addAttribute("memberView", adminService.memberView(memberNumber));
		return "admin/AdminMemberInfo";
	}
	
	//회원 계약 수정 페이지
	@RequestMapping("adminContractModifyView")
	public String contractModifyView(Model model, int contractNumber) {
		model.addAttribute("contract", adminService.contractModifyView(contractNumber));
		return "admin/AdminContractModify";
	}
	
	//회원 계약 수정
	@RequestMapping("adminContractModify")
	public String contractModify(Model model, ContractDTO contractDTO) {
		adminService.contractModify(contractDTO);
		model.addAttribute("memberView", adminService.memberView(contractDTO.getMemberNumber()));
		return "admin/AdminMemberInfo";
	}

	// question

	@RequestMapping("adminQuestionList")
	public String questionList(Model model) {
		return "admin/AdminQuestionList";
	}

	// visit

	// 방문신청 리스트 페이지
	@RequestMapping("adminVisitList")
	public String visitList(Model model, HttpServletRequest request) {
		model.addAttribute("visitListView", adminService.visitList());
		return "admin/AdminVisitList";
	}
	
	//특정 인원 방문신청 리스트
	@RequestMapping("adminVisitListOfMember")
	public String visitListOfMember(Model model, @RequestParam("memberNumber") int memberNumber) {
		model.addAttribute("visitListView", adminService.visitListOfMember(memberNumber));
		return "admin/AdminVisitList";
	}

	// 방문신청 글 보기
	@RequestMapping("adminVisitView")
	public String adminVisitView(Model model, @RequestParam("writingNumber") int writingNumber) {
		model.addAttribute("visitView", adminService.visitView(writingNumber));
		return "admin/AdminVisitCheck";
	}

	// 방문신청 확인 체크
	@RequestMapping("adminVisitCheck")
	public String adminVisitCheck(Model model, @RequestParam("writingNumber") int writingNumber) {
		adminService.visitCheck(writingNumber);
		return "redirect:adminVisitList";
	}

	// branch

	// 지점 및 방 정보 작성 페이지
	@RequestMapping("adminBranchRegistView")
	public String adminBranchRegistView() {
		return "admin/AdminBranchRegist";
	}

	// 지점 등록
	@RequestMapping("adminBranchRegist")
	public String adminBranchRegist(Model model, BranchDTO branchDTO, RoomDTO roomDTO) {
//		String path = request.getSession().getServletContext().getRealPath("/");
//		System.out.println(path);

		// 경로 가져오기
//		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
//		Resource resource = defaultresourceloader.getResource("file:src/main/resource/static" + "/1.jpg");
//		System.out.println(resource);

		// 지점 및 방 등록
		adminService.branchRegist(branchDTO, roomDTO);
		int branchNumber = adminService.getBranchNumber();
		adminService.roomRegist(branchNumber, roomDTO);
		adminService.branchImageRegist(branchNumber, branchDTO);
		adminService.roomImageRegist(branchNumber, roomDTO);

		// 이미지 업로드 하기(지점이미지는 거실,부엌,화장실,기타 / 방이미지는 방마다 이미지 1개)
		// 1.완료버튼을 누르면 브랜치넘버를 다음 페이지로 보내고, 그 브랜치넘버를 다음페이지 뷰에 넣어둔다.
		// 2.브랜치넘버에 따라 룸 셀렉트를 하고, 뷰에서 포이치로 생성한다.

		return "redirect:/";
	}

	// 지점등록(이미지)
	@RequestMapping("branchRegistComplete")
	public String branchImage(List<MultipartFile> imageBranch, List<MultipartFile> imageRoom) {

		// 브랜치넘버가져오기
		int branchNumber = adminService.getBranchNumber();

		// 지점업로드 절대경로,상대경로
		String BranchUploadFolder1 = "/Users/gwon-oyeob/knockknock/knockknock/knockknock/KnockKnock/src/main/resources/static/images/branch";
		String BranchUploadFolder2 = "/images/branch";
		// 룸업로드 절대경로,상대경로
		String RoomUploadFolder1 = "/Users/gwon-oyeob/knockknock/knockknock/knockknock/KnockKnock/src/main/resources/static/images/room";
		String RoomUploadFolder2 = "/images/room";
		// 경로생성
		File BranchUploadPath = new File(BranchUploadFolder1);
		File RoomUploadPath = new File(RoomUploadFolder1);

		if (BranchUploadPath.exists() == false) {
			BranchUploadPath.mkdirs();
		}
		if (RoomUploadPath.exists() == false) {
			RoomUploadPath.mkdirs();
		}

		// 지점사진 업로드---
		for (MultipartFile multipartFile : imageBranch) {
			String uploadFileName = branchNumber + "BRANCH@" + multipartFile.getOriginalFilename(); // 브랜치넘버+파일명
			// IE에서 uploadFileName이 풀경로로 나와서, 파일명 이전 경로는 짜르는 작업. 실제 파일명이 나온다.
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
			int idx = uploadFileName.indexOf("@");
			uploadFileName = uploadFileName.substring(0, idx);

			try {
				File saveFile = new File(BranchUploadPath, uploadFileName);
				// 경로를 파일화시킨다.(실제파일생성)
				multipartFile.transferTo(saveFile);
				// DB에 저장하기 위해 상대경로명에 유저아이디를 섞은 파일명을 합쳐서 finalImage라는 DB용 경로명을 만든다.
				String finalImage = BranchUploadFolder2 + uploadFileName;
				// 이미지경로를 저장한다.
//				memberService.saveImageDir(finalImage,username);
				// 이미지 경로를 불러온다.(뷰에서 받아 쓰기 위한 용도)
//				model.addAttribute("image",memberService.getImageDir(username));
			} catch (Exception e) {
				e.getMessage();
			} // end catch
		}

		// 방사진 업로드---
		for (MultipartFile multipartFile : imageRoom) {
			String uploadFileName = branchNumber + "호점@" + multipartFile.getOriginalFilename(); // 브랜치넘버+파일명
			// IE에서 uploadFileName이 풀경로로 나와서, 파일명 이전 경로는 짜르는 작업. 실제 파일명이 나온다.
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);

			try {
				File saveFile = new File(RoomUploadPath, uploadFileName);
				// 경로를 파일화시킨다.(실제파일생성)
				multipartFile.transferTo(saveFile);
				// DB에 저장하기 위해 상대경로명에 유저아이디를 섞은 파일명을 합쳐서 finalImage라는 DB용 경로명을 만든다.
				String finalImage = RoomUploadFolder2 + uploadFileName;
				// 이미지경로를 저장한다.
//				memberService.saveImageDir(finalImage,username);
				// 이미지 경로를 불러온다.(뷰에서 받아 쓰기 위한 용도)
//				model.addAttribute("image",memberService.getImageDir(username));
			} catch (Exception e) {
				e.getMessage();
			} // end catch
		}

		return "home/Home";
	}

	// 지점 정보 수정 페이지
	@RequestMapping("adminBranchModifyView")
	public String adminBranchModifyView(Model model, @RequestParam("branchNumber") int branchNumber) {
		model.addAttribute("branchDTO", adminService.branchModifyView(branchNumber));
		model.addAttribute("roomVDTO", adminService.roomModifyView(branchNumber));
		model.addAttribute("branchImage", adminService.branchModifyViewImages(branchNumber));
		model.addAttribute("roomImage", adminService.roomModifyViewImages(branchNumber));
		int restRoom = 8 - adminService.roomModifyView(branchNumber).size();
		model.addAttribute("restRoom", restRoom);
		logger.info(adminService.roomModifyView(branchNumber).get(0).getGender());
		adminService.branchModifyView(branchNumber).getGender();
		return "admin/AdminBranchModify";
	}

	// 지점 정보 수정
	@RequestMapping("adminBranchModify")
	public String adminBranchModify(Model model, BranchDTO branchDTO, RoomDTO roomDTO, AddRoomDTO addRoomDTO) {
		adminService.BranchModify(branchDTO, roomDTO, addRoomDTO);
		adminService.roomModify(branchDTO, roomDTO, addRoomDTO);
		return "redirect:/";
	}

	// 등록테스트
	@RequestMapping("registTest")
	public String test(BranchDTO branchDTO, RoomDTO roomDTO) {
		return "/admin/AdminTest";
	}

}
