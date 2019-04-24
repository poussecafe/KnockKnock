package com.knockknock.controller;

import java.io.File;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.knockknock.dto.branch.BranchDTO;
import com.knockknock.dto.branch.RoomDTO;
import com.knockknock.service.AdminService;

@Controller
public class BackupAdminController {

//	@Autowired
//	public AdminService adminService;
//
//	// event
//
//	// 이벤트 리스트 페이지
//	@RequestMapping("adminEventListView")
//	public String eventListView(Model model) {
//		model.addAttribute("eventListView", adminService.eventList());
//		return "admin/AdminEventList";
//	}
//
//	// 이벤트 글 보기
//	@RequestMapping("adminEventView")
//	public String eventView(Model model, @RequestParam("writingNumber") int writingNumber) {
//		model.addAttribute("eventView", adminService.eventView(writingNumber));
//		return "admin/AdminEventPost";
//	}
//
//	// 이벤트 작성 페이지
//	@RequestMapping("adminEventWriteView")
//	public String eventWriteView(Model model) {
//		return "admin/AdminEventWrite";
//	}
//
//	// 이벤트 등록
//	@RequestMapping("adminEventWrite")
//	public String eventWrite(Model model, @RequestParam("memberNumber") int memberNumber,
//			@RequestParam("title") String title, @RequestParam("content") String content,
//			@RequestParam("eventStartTime") Date eventStartTime, @RequestParam("eventEndTime") Date eventEndTime,
//			@RequestParam("acceptStartTime") Date acceptStartTime, @RequestParam("acceptEndTime") Date acceptEndTime,
//			@RequestParam("recruitNumber") int recruitNumber) {
////		int writingNumber = 
//		adminService.eventWrite(memberNumber, title, content, eventStartTime, eventEndTime, acceptStartTime,
//				acceptEndTime, recruitNumber);
////		model.addAttribute("eventView", adminService.eventView(writingNumber));
////		return "redirect:admin/AdminEventPost";
//		return "redirect:adminEventListView";
//	}
//
//	// 이벤트 수정 페이지
//	@RequestMapping("adminEventModifyView")
//	public String eventModifyView(Model model, @RequestParam("writingNumber") int writingNumber) {
//		model.addAttribute("eventModifyView", adminService.eventModifyView(writingNumber));
//		return "admin/AdminEventModify";
//	}
//
//	// 이벤트 수정
//	@RequestMapping("adminEventModify")
//	public String eventModify(Model model, @RequestParam("writingNumber") int writingNumber,
//			@RequestParam("memberNumber") int memberNumber, @RequestParam("title") String title,
//			@RequestParam("content") String content, @RequestParam("eventStartTime") Date eventStartTime,
//			@RequestParam("eventEndTime") Date eventEndTime, @RequestParam("acceptStartTime") Date acceptStartTime,
//			@RequestParam("acceptEndTime") Date acceptEndTime, @RequestParam("recruitNumber") int recruitNumber) {
//		adminService.eventModify(writingNumber, memberNumber, title, content, eventStartTime, eventEndTime,
//				acceptStartTime, acceptEndTime, recruitNumber);
//		return "redirect:adminEventListView";
//	}
//
//	// 이벤트 삭제
//	@RequestMapping("adminEventDelete")
//	public String eventDelete(Model model, @RequestParam("writingNumber") int writingNumber) {
//		adminService.eventDelete(writingNumber);
//		return "redirect:adminEventListView";
//	}
//
//	// member
//
//	@RequestMapping("adminMemberSearchView")
//	public String memberSearchView(Model model) {
//		return "admin/AdminMemberSearch";
//	}
//
//	@RequestMapping("adminMemberSearch")
//	public String memberSearch(Model model, @RequestParam("keyword") String keyword) {
//		model.addAttribute("memberListView", adminService.memberListView(keyword));
//		return "admin/AdminMemberSearch";
//	}
//
//	@RequestMapping("adminMemberView")
//	public String memberView(Model model, @RequestParam("memberNumber") int memberNumber) {
//		model.addAttribute("memberView", adminService.memberView(memberNumber));
//		return "admin/AdminMemberInfo";
//	}
//
//	@RequestMapping("adminContractRegistView")
//	public String contractRegistView(Model model, @RequestParam("memberNumber") int memberNumber) {
//		model.addAttribute("memberNumber", memberNumber);
//		return "admin/AdminContractRegist";
//	}
//
//	@RequestMapping("adminContractRegist")
//	public String contractRegist(Model model, @RequestParam("memberNumber") int memberNumber,
//			@RequestParam("branchNumber") int branchNumber, @RequestParam("roomNumber") int roomNumber,
//			@RequestParam("period") int period, @RequestParam("isPet") String isPet,
//			@RequestParam("emergencyNumber") String emergencyNumber, @RequestParam("bankName") String bankName,
//			@RequestParam("depositor") String depositor, @RequestParam("memberAccount") String memberAccount,
//			@RequestParam("contractDate") Date contractDate, @RequestParam("idNumber") String idNumber,
//			@RequestParam("memo") String memo) {
//		adminService.contractRegist(memberNumber, branchNumber, roomNumber, period, isPet, emergencyNumber, bankName,
//				depositor, memberAccount, contractDate, idNumber, memo);
//		adminService.setReturnAmount(adminService.getContractNumber(),
//				adminService.getDeposit(roomNumber, branchNumber));
//		return "redirect:/";
//	}
//
//	// question
//
//	@RequestMapping("adminQuestionList")
//	public String questionList(Model model) {
//		return "admin/AdminQuestionList";
//	}
//
//	// visit
//
//	// 방문신청 리스트 페이지
//	@RequestMapping("adminVisitList")
//	public String visitList(Model model) {
//		model.addAttribute("visitListView", adminService.visitList());
//		return "admin/AdminVisitList";
//	}
//
//	// 방문신청 글 보기
//	@RequestMapping("adminVisitView")
//	public String adminVisitView(Model model, @RequestParam("writingNumber") int writingNumber) {
//		model.addAttribute("visitView", adminService.visitView(writingNumber));
//		return "admin/AdminVisitCheck";
//	}
//
//	// 방문신청 확인 체크
//	@RequestMapping("adminVisitCheck")
//	public String adminVisitCheck(Model model, @RequestParam("writingNumber") int writingNumber) {
//		adminService.visitCheck(writingNumber);
//		return "redirect:adminVisitList";
//	}
//
//	// branch
//
//	// 지점 및 방 정보 작성 페이지
//	@RequestMapping("adminBranchRegistView")
//	public String adminBranchRegistView() {
//		return "admin/AdminBranchRegist";
//	}
//
//	// 지점 등록
//	@RequestMapping("adminBranchRegist")
//	public String adminBranchRegist(Model model,
//			@RequestParam(value = "theme", required = true, defaultValue = "없음") String theme,
//			@RequestParam(value = "bankName", required = true) String bankName,
//			@RequestParam(value = "depositor", required = true, defaultValue = "knockknock") String depositor,
//			@RequestParam(value = "branchAccount", required = true) String branchAccount,
//			@RequestParam(value = "gender", required = true, defaultValue = "공용") String gender,
//			@RequestParam(value = "branchType", required = true) String branchType,
//			@RequestParam(value = "introduce", required = true, defaultValue = "") String introduce,
//			@RequestParam(value = "isParking", required = true) String isParking,
//			@RequestParam(value = "isElevator", required = true) String isElevator,
//			@RequestParam(value = "pet", required = true) String pet,
//			@RequestParam(value = "address", required = true) String address,
//			@RequestParam(value = "addressDetail", required = false, defaultValue = "") String addressDetail,
//			@RequestParam(value = "remainAddress", required = true, defaultValue = "") String remainAddress,
//			@RequestParam(value = "roomNumber1", required = true, defaultValue = "0") int roomNumber1,
//			@RequestParam(value = "roomNumber2", required = false, defaultValue = "0") int roomNumber2,
//			@RequestParam(value = "roomNumber3", required = false, defaultValue = "0") int roomNumber3,
//			@RequestParam(value = "roomNumber4", required = false, defaultValue = "0") int roomNumber4,
//			@RequestParam(value = "roomNumber5", required = false, defaultValue = "0") int roomNumber5,
//			@RequestParam(value = "roomNumber6", required = false, defaultValue = "0") int roomNumber6,
//			@RequestParam(value = "roomNumber7", required = false, defaultValue = "0") int roomNumber7,
//			@RequestParam(value = "roomNumber8", required = false, defaultValue = "0") int roomNumber8,
//			@RequestParam(value = "roomGender1", required = true, defaultValue = "") String roomGender1,
//			@RequestParam(value = "roomGender2", required = false, defaultValue = "") String roomGender2,
//			@RequestParam(value = "roomGender3", required = false, defaultValue = "") String roomGender3,
//			@RequestParam(value = "roomGender4", required = false, defaultValue = "") String roomGender4,
//			@RequestParam(value = "roomGender5", required = false, defaultValue = "") String roomGender5,
//			@RequestParam(value = "roomGender6", required = false, defaultValue = "") String roomGender6,
//			@RequestParam(value = "roomGender7", required = false, defaultValue = "") String roomGender7,
//			@RequestParam(value = "roomGender8", required = false, defaultValue = "") String roomGender8,
//			@RequestParam(value = "roomType1", required = true, defaultValue = "") String roomType1,
//			@RequestParam(value = "roomType2", required = false, defaultValue = "") String roomType2,
//			@RequestParam(value = "roomType3", required = false, defaultValue = "") String roomType3,
//			@RequestParam(value = "roomType4", required = false, defaultValue = "") String roomType4,
//			@RequestParam(value = "roomType5", required = false, defaultValue = "") String roomType5,
//			@RequestParam(value = "roomType6", required = false, defaultValue = "") String roomType6,
//			@RequestParam(value = "roomType7", required = false, defaultValue = "") String roomType7,
//			@RequestParam(value = "roomType8", required = false, defaultValue = "") String roomType8,
//			@RequestParam(value = "roomSpace1", required = true, defaultValue = "") String roomSpace1,
//			@RequestParam(value = "roomSpace2", required = false, defaultValue = "") String roomSpace2,
//			@RequestParam(value = "roomSpace3", required = false, defaultValue = "") String roomSpace3,
//			@RequestParam(value = "roomSpace4", required = false, defaultValue = "") String roomSpace4,
//			@RequestParam(value = "roomSpace5", required = false, defaultValue = "") String roomSpace5,
//			@RequestParam(value = "roomSpace6", required = false, defaultValue = "") String roomSpace6,
//			@RequestParam(value = "roomSpace7", required = false, defaultValue = "") String roomSpace7,
//			@RequestParam(value = "roomSpace8", required = false, defaultValue = "") String roomSpace8,
//			@RequestParam(value = "roomDeposit1", required = true, defaultValue = "0") int roomDeposit1,
//			@RequestParam(value = "roomDeposit2", required = false, defaultValue = "0") int roomDeposit2,
//			@RequestParam(value = "roomDeposit3", required = false, defaultValue = "0") int roomDeposit3,
//			@RequestParam(value = "roomDeposit4", required = false, defaultValue = "0") int roomDeposit4,
//			@RequestParam(value = "roomDeposit5", required = false, defaultValue = "0") int roomDeposit5,
//			@RequestParam(value = "roomDeposit6", required = false, defaultValue = "0") int roomDeposit6,
//			@RequestParam(value = "roomDeposit7", required = false, defaultValue = "0") int roomDeposit7,
//			@RequestParam(value = "roomDeposit8", required = false, defaultValue = "0") int roomDeposit8,
//			@RequestParam(value = "roomMonthlyRent1", required = true, defaultValue = "0") int roomMonthlyRent1,
//			@RequestParam(value = "roomMonthlyRent2", required = false, defaultValue = "0") int roomMonthlyRent2,
//			@RequestParam(value = "roomMonthlyRent3", required = false, defaultValue = "0") int roomMonthlyRent3,
//			@RequestParam(value = "roomMonthlyRent4", required = false, defaultValue = "0") int roomMonthlyRent4,
//			@RequestParam(value = "roomMonthlyRent5", required = false, defaultValue = "0") int roomMonthlyRent5,
//			@RequestParam(value = "roomMonthlyRent6", required = false, defaultValue = "0") int roomMonthlyRent6,
//			@RequestParam(value = "roomMonthlyRent7", required = false, defaultValue = "0") int roomMonthlyRent7,
//			@RequestParam(value = "roomMonthlyRent8", required = false, defaultValue = "0") int roomMonthlyRent8,
//			@RequestParam(value = "roomRentableDate1", required = true) String roomRentableDate1,
//			@RequestParam(value = "roomRentableDate2", required = false) String roomRentableDate2,
//			@RequestParam(value = "roomRentableDate3", required = false) String roomRentableDate3,
//			@RequestParam(value = "roomRentableDate4", required = false) String roomRentableDate4,
//			@RequestParam(value = "roomRentableDate5", required = false) String roomRentableDate5,
//			@RequestParam(value = "roomRentableDate6", required = false) String roomRentableDate6,
//			@RequestParam(value = "roomRentableDate7", required = false) String roomRentableDate7,
//			@RequestParam(value = "roomRentableDate8", required = false) String roomRentableDate8,
//			@RequestParam(value = "publicFacility", required = false, defaultValue = "없음") String publicFacility,
//			@RequestParam(value = "privateFacility", required = false, defaultValue = "없음") String privateFacility,
//			@RequestParam(value = "rule") String rule) {
//
//		// 배열로 정리
//		int[] roomNumber = { roomNumber1, roomNumber2, roomNumber3, roomNumber4, roomNumber5, roomNumber6, roomNumber7,
//				roomNumber8 };
//		String[] roomGender = { roomGender1, roomGender2, roomGender3, roomGender4, roomGender5, roomGender6,
//				roomGender7, roomGender8 };
//		String[] roomType = { roomType1, roomType2, roomType3, roomType4, roomType5, roomType6, roomType7, roomType8 };
//		String[] roomSpace = { roomSpace1, roomSpace2, roomSpace3, roomSpace4, roomSpace5, roomSpace6, roomSpace7,
//				roomSpace8 };
//		int[] roomDeposit = { roomDeposit1, roomDeposit2, roomDeposit3, roomDeposit4, roomDeposit5, roomDeposit6,
//				roomDeposit7, roomDeposit8 };
//		int[] roomMonthlyRent = { roomMonthlyRent1, roomMonthlyRent2, roomMonthlyRent3, roomMonthlyRent4,
//				roomMonthlyRent5, roomMonthlyRent6, roomMonthlyRent7, roomMonthlyRent8 };
//		String[] roomRentableDate = { roomRentableDate1, roomRentableDate2, roomRentableDate3, roomRentableDate4,
//				roomRentableDate5, roomRentableDate6, roomRentableDate7, roomRentableDate8 };
//
//		// 지점 및 방 등록
//		adminService.branchRegist(theme, bankName, depositor, branchAccount, gender, introduce, branchType, isParking,
//				isElevator, pet, address, addressDetail, remainAddress, publicFacility, rule, roomType);
//		int branchNumber = adminService.getBranchNumber();
//		adminService.roomRegist(branchNumber, roomNumber, roomGender, roomType, roomSpace, roomDeposit, roomMonthlyRent,
//				roomRentableDate, privateFacility);
//		
//		model.addAttribute("branchNumber",branchNumber);
//		//이미지 업로드 하기(지점이미지는 거실,부엌,화장실,기타 / 방이미지는 방마다 이미지 1개)
//		//1.완료버튼을 누르면 브랜치넘버를 다음 페이지로 보내고, 그 브랜치넘버를 다음페이지 뷰에 넣어둔다.
//		//2.브랜치넘버에 따라 룸 셀렉트를 하고, 뷰에서 포이치로 생성한다.
//
//		return "admin/AdminBranchRegist2";
//	}
//	
//	//지점등록(이미지)
//	@RequestMapping("branchRegistComplete")
//	public String branchImage(List<MultipartFile> imageBranch, List<MultipartFile> imageRoom){
//		
//		//브랜치넘버가져오기
//		int branchNumber = adminService.getBranchNumber(); 
//		
//		//지점업로드 절대경로,상대경로
//		String BranchUploadFolder1 = "/Users/gwon-oyeob/knockknock/knockknock/knockknock/KnockKnock/src/main/resources/static/images/branch";
//		String BranchUploadFolder2 = "/images/branch";
//		//룸업로드 절대경로,상대경로
//		String RoomUploadFolder1 = "/Users/gwon-oyeob/knockknock/knockknock/knockknock/KnockKnock/src/main/resources/static/images/room";
//		String RoomUploadFolder2 = "/images/room";
//		//경로생성
//		File BranchUploadPath = new File(BranchUploadFolder1);
//		File RoomUploadPath = new File(RoomUploadFolder1);
//		
//		if(BranchUploadPath.exists() == false) {
//			BranchUploadPath.mkdirs();
//		}
//		if(RoomUploadPath.exists()==false) {
//			RoomUploadPath.mkdirs();
//		}
//		
//		//지점사진 업로드---
//		for(MultipartFile multipartFile : imageBranch) {
//			String uploadFileName = branchNumber+"BRANCH@"+multipartFile.getOriginalFilename(); //브랜치넘버+파일명
//			//IE에서 uploadFileName이 풀경로로 나와서, 파일명 이전 경로는 짜르는 작업. 실제 파일명이 나온다.
//			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
//			int idx = uploadFileName.indexOf("@");
//			uploadFileName = uploadFileName.substring(0,idx);
//			
//			
//			try {
//				File saveFile = new File(BranchUploadPath, uploadFileName);
//				//경로를 파일화시킨다.(실제파일생성)
//				multipartFile.transferTo(saveFile);
//				//DB에 저장하기 위해 상대경로명에 유저아이디를 섞은 파일명을 합쳐서 finalImage라는 DB용 경로명을 만든다.
//				String finalImage = BranchUploadFolder2+uploadFileName;
//				//이미지경로를 저장한다.
////				memberService.saveImageDir(finalImage,username);
//				//이미지 경로를 불러온다.(뷰에서 받아 쓰기 위한 용도)
////				model.addAttribute("image",memberService.getImageDir(username));
//			}catch(Exception e) {
//				e.getMessage();
//			}//end catch
//		}
//		
//		//방사진 업로드---
//		for(MultipartFile multipartFile : imageRoom) {
//			String uploadFileName = branchNumber+"호점@"+multipartFile.getOriginalFilename(); //브랜치넘버+파일명
//			//IE에서 uploadFileName이 풀경로로 나와서, 파일명 이전 경로는 짜르는 작업. 실제 파일명이 나온다.
//			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
//			
//			try {
//				File saveFile = new File(RoomUploadPath, uploadFileName);
//				//경로를 파일화시킨다.(실제파일생성)
//				multipartFile.transferTo(saveFile);
//				//DB에 저장하기 위해 상대경로명에 유저아이디를 섞은 파일명을 합쳐서 finalImage라는 DB용 경로명을 만든다.
//				String finalImage = RoomUploadFolder2+uploadFileName;
//				//이미지경로를 저장한다.
////				memberService.saveImageDir(finalImage,username);
//				//이미지 경로를 불러온다.(뷰에서 받아 쓰기 위한 용도)
////				model.addAttribute("image",memberService.getImageDir(username));
//			}catch(Exception e) {
//				e.getMessage();
//			}//end catch
//		}
//		
//		return "home/Home";
//	}
//	
//	// 지점 정보 수정 페이지
//	@RequestMapping("adminBranchModifyView")
//	public String adminBranchModifyView(Model model) {
//		return "";
//	}
//
//	// 지점 정보 수정
//	@RequestMapping("adminBranchModify")
//	public String adminBranchModify(Model model) {
//		return "";
//	}
//	
//	//등록테스트
//	@RequestMapping("registTest")
//	public String test(BranchDTO branchDTO, RoomDTO roomDTO){
//		return "/admin/AdminTest";
//	}

}
