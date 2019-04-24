package com.knockknock.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.knockknock.dto.event.MeetingVDTO;
import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.ProfileVDTO;
import com.knockknock.security.MemberController;
import com.knockknock.security.MemberService;

@Controller
public class MyPageController {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberController mc;

	private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);

	@RequestMapping("/profileMain")
	public String profileMain(Model model, ProfileVDTO profileVDTO, Authentication authentication) {
		// 현재 로그인 사용자 정보에 접근
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());

		String username = user.getUsername();

		//프로필정보를 불러오기
		model.addAttribute("profile", memberService.getProfile(username));
		model.addAttribute("getPet", memberService.getPet(username));
		model.addAttribute("image", memberService.getImageDir(username));

		return "member/MyProfile";
	}

	@RequestMapping("/updateProfileComplete")
	@ResponseBody
	public List<MemberDTO> profileUpdate(Model model, @RequestBody ProfileVDTO profileVDTO,
		Authentication authentication,HttpSession hs,MemberDTO memberDTO) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String username = user.getUsername();
		memberDTO.setEmail(user.getUsername());
		
		if (profileVDTO.getAnimal().length() > 0) {
			memberService.deletePet(profileVDTO);//펫초기화
			memberService.firstMyPetUpdate(profileVDTO);//펫값있으면 넣기
			memberService.profileUpdate2(profileVDTO);//업뎃
			
		} else {
			memberService.deletePet(profileVDTO);//펫초기화
			memberService.profileUpdate2(profileVDTO);//값없는채로 업뎃
		}
		mc.getSession(authentication, hs, memberDTO);
		return memberService.getProfile(username);
	}

	// 프로필사진업로드
	@RequestMapping(value = "/profileUpdate")
	@ResponseBody
	public MemberDTO uploadFormPost(@RequestBody MultipartFile[] uploadFile, Model model, MemberDTO memberDTO,
			Authentication authentication,HttpSession hs) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String username = user.getUsername();
		
		//업로드할 절대경로1(리눅스(서버)에서 절대경로에서 가져오도록 수정)
		String uploadFolder;
		
		String OS = System.getProperty("os.name").toLowerCase();
		if(OS.indexOf("nux") >= 0) {
			uploadFolder = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images";
		} else {
			uploadFolder = System.getProperty("user.dir")+"/src/main/resources/static/images";
		}
		
		//업로드할 절대경로2
		String uploadFolderPath = "/profile";
		//DB에 저장할 상대경로
		String uploadRelativeDirectory = "/images/profile/";
		
		//절대경로1,2를 합쳐서 실제 업로드 경로를 만든다.(2를 1에 함께 써도 무관)
		File uploadPath = new File(uploadFolder,uploadFolderPath);
		
		//업로드 경로가 존재하지않으면 폴더를 만든다.
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		String finalImage ="";
		
		// 다중파일의 경우를 위해 포문으로 파일들을 가져온다(프로필사진 1개니까 상관없음)
		for (MultipartFile multipartFile : uploadFile) {

			String uploadFileName = username + "@" + multipartFile.getOriginalFilename(); // 유저아이디+파일명

			// IE에서 uploadFileName이 풀경로로 나와서, 파일명 이전 경로는 짜르는 작업. 실제 파일명이 나온다.
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
		
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				// 경로를 파일화시킨다.(실제파일생성)
				multipartFile.transferTo(saveFile);
				// DB에 저장하기 위해 상대경로명에 유저아이디를 섞은 파일명을 합쳐서 finalImage라는 DB용 경로명을 만든다.
				finalImage = uploadRelativeDirectory + uploadFileName;
				// 이미지경로를 저장한다.
				memberService.saveImageDir(finalImage, username);
				// 이미지 경로를 불러온다.(뷰에서 받아 쓰기 위한 용도)
				model.addAttribute("image", memberService.getImageDir(username));
			} catch (Exception e) {
				e.getMessage();
			} // end catch
		}
		
		
		hs.removeAttribute("nickname");
		hs.removeAttribute("profileImage");
		
		memberDTO.setEmail(username);
		memberDTO.setImageProfile(finalImage);
		
		mc.getSession(authentication, hs, memberDTO);
		
		return memberService.getImageDir(username);
	}

	@RequestMapping("/MyEventList")
	public String myEventList(Model model, ProfileVDTO profileVDTO) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());

		model.addAttribute("MEL", memberService.getMEL(user.getUsername()));

		return "member/MyEventList";
	}

	@RequestMapping("/MyMeetingList")
	public String myMeetingList(Model model, ProfileVDTO profileVDTO) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());
		System.out.println(user.getUsername());

		model.addAttribute("MMLJ", memberService.getMMLJ(user.getUsername()));
		model.addAttribute("MMLM", memberService.getMMLM(user.getUsername()));

		return "member/MyMeetingList";
	}

	@RequestMapping("/MyVisitList")
	public String myVisitList(Model model, ProfileVDTO profileVDTO) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());

		model.addAttribute("MVL", memberService.getMVL(user.getUsername()));
		return "member/MyVisitList";
	}

	@RequestMapping("/MyLikeBranch")
	public String myLikeBranch(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();

		model.addAttribute("MLBL", memberService.getMLBL(email));

		return "member/MyLikeBranch";
	}

	// 내 관심 지점 삭제
	@RequestMapping(value = "/deleteLB", method = RequestMethod.POST)
	@ResponseBody
	public String deleteLB(Model model, @RequestBody String branchNumber1) {
		System.out.println(branchNumber1);
		
		 int branchNumber = Integer.parseInt(branchNumber1);
		 
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		 User user = (User)authentication.getPrincipal(); 
		 String email = user.getUsername();
		 
		 memberService.deleteLB(branchNumber, email);
		 
		 model.addAttribute("MLBL", memberService.getMLBL(email));

		return "member/MyLikeBranch";
	}

	// 신청한 모임 취소 - 각각
	@RequestMapping("/deleteJM")
	public String deleteJM(Model model, @RequestParam("writingNumber") int writingNumber) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());
		memberService.deleteJM(writingNumber, user.getUsername());

		model.addAttribute("MMLJ", memberService.getMMLJ(user.getUsername()));
		model.addAttribute("MMLM", memberService.getMMLM(user.getUsername()));

		return "member/MyMeetingList";
	}

	// 신청한 모임 취소 - 체크박스
	@RequestMapping("/checkedDeleteJM")
	@ResponseBody
	public String checkedDeleteJM(Model model, @RequestBody String[] checkBoxArr) {
		// JSON.stringify를 통해 배열로 넘어온 값은 @RequestBody로 받아야 한다

		System.out.println(checkBoxArr[0]);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		for (int i = 0; i < checkBoxArr.length; i++) {
			int writingNumber = Integer.parseInt(checkBoxArr[i]);
			memberService.deleteJM(writingNumber, user.getUsername());
		}

		model.addAttribute("MMLJ", memberService.getMMLJ(user.getUsername()));
		model.addAttribute("MMLM", memberService.getMMLM(user.getUsername()));

		return "member/MyMeetingList";
	}
	
	// 개설한 모임 취소 - 마이 페이지
	@RequestMapping("/deleteMM")
	public String deleteMM(Model model, @RequestParam("writingNumber") int writingNumber) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());
		memberService.deleteMM(writingNumber, user.getUsername());

		model.addAttribute("MMLJ", memberService.getMMLJ(user.getUsername()));
		model.addAttribute("MMLM", memberService.getMMLM(user.getUsername()));

		return "member/MyMeetingList";
	}

	// 참가한 이벤트 취소 - 각각
	@RequestMapping("/deleteJE")
	public String deleteJE(Model model, @RequestParam("writingNumber") int writingNumber) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());
		memberService.deleteJE(writingNumber, user.getUsername());

		model.addAttribute("MEL", memberService.getMEL(user.getUsername()));

		return "member/MyEventList";
	}

	// 참가한 이벤트 취소 - 체크박스
	@RequestMapping("/checkedDeleteJE")
	@ResponseBody
	public String checkedDeleteJE(Model model, @RequestBody String[] checkBoxArr) {
		// JSON.stringify를 통해 배열로 넘어온 값은 @RequestBody로 받아야 한다

		System.out.println(checkBoxArr[0]);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		for (int i = 0; i < checkBoxArr.length; i++) {
			int writingNumber = Integer.parseInt(checkBoxArr[i]);
			memberService.deleteJE(writingNumber, user.getUsername());
		}

		model.addAttribute("MEL", memberService.getMEL(user.getUsername()));

		return "member/MyEventList";
	}

	// 방문 신청 취소
	@RequestMapping("/deleteV")
	public String deleteV(Model model, @RequestParam("writingNumber") int writingNumber) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		model.addAttribute("user", user.getUsername());
		memberService.deleteV(writingNumber, user.getUsername());

		model.addAttribute("MVL", memberService.getMVL(user.getUsername()));

		return "member/MyVisitList";
	}

	// 비밀번호변경
	@RequestMapping("changePass")
	public String changePass(Model model, MemberDTO memberDTO) {
		// 현재 로그인 사용자 정보에 접근
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		System.out.println("접속아이디:" + user.getUsername());
		System.out.println("입력비밀번호:" + memberDTO.getPassword());
		String username = user.getUsername();

		// 현재로그인 아이디를 DTO에 저장
		memberDTO.setEmail(username);
		// 프로필정보를 불러오기 위한 모델
		model.addAttribute("profile", memberService.getProfile(username));
		// 펫정보 불러오기 위한 모델
		model.addAttribute("getPet", memberService.getPet(username));
		//프로필메인을 불러올 때, 이미지도 불러오기 위한 모델
		model.addAttribute("image", memberService.getImageDir(username));

		memberService.changeRealPassword(memberDTO);
		return "member/MyProfile";
	}

	// 개설한 모임 취소(사실상 UPDATE) - 모임 상세 페이지에서
	@RequestMapping(value = "/cancelMM", method = RequestMethod.POST)
	@ResponseBody
	public void cancelMM(@RequestBody MeetingVDTO meetingVDTO, Authentication authentication, Model model) {

		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();

		logger.info("POST/cancelMM");
		logger.info(meetingVDTO+"");
		
		memberService.cancelMM(meetingVDTO, email);
		memberService.cancelMM2(meetingVDTO, email);

		model.addAttribute("MMLJ", memberService.getMMLJ(user.getUsername()));
		model.addAttribute("MMLM", memberService.getMMLM(user.getUsername()));
		
	}
	
	// 모임 취소 사유 확인(SELECT)
	@RequestMapping("/confirmReason")
	public String ConfirmReason(Model model, @RequestParam int writingNumber, Authentication authentication) {
		
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();
		
		model.addAttribute("confirmReason", memberService.confirmReason(writingNumber));
		System.out.println(memberService.confirmReason(writingNumber));
		model.addAttribute("MMLJ", memberService.getMMLJ(email));
		model.addAttribute("MMLM", memberService.getMMLM(email));
		
		return "member/MyMeetingList";
	}
	
	// 이벤트 취소 사유 확인(SELECT)
	@RequestMapping("/confirmReasonEvent")
	public String ConfirmReasonEvent(Model model, @RequestParam int writingNumber, Authentication authentication) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();
		
		model.addAttribute("confirmReasonEvent", memberService.confirmReasonEvent(writingNumber));
				
		model.addAttribute("MEL", memberService.getMEL(email));
				
		return "member/MyEventList";
	}

}
