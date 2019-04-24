package com.knockknock.security;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.PetDTO;
import com.knockknock.mapper.MemberMapper;
import com.knockknock.social.SocialService;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	SqlSession sqlsession;
	@Autowired
	SocialService socialService;

	// 회원가입으로 이동
	@RequestMapping("/register")
	public String registerStart() {
		return "member/Register";
	}

	// 회원가입폼(일반회원가입)
	// 1.회원등록
	@RequestMapping("/create")
	public String member(Model model, MemberDTO memberDTO, PetDTO petDTO) {
		// 2.memberService의 register호출
		memberService.register(memberDTO, petDTO);

		return "member/RegisterComplete";
	}
	
	@RequestMapping("/suvCreate")
	public String socialMember(Model model, MemberDTO memberDTO, PetDTO petDTO) {
		
		memberService.suvRegister(memberDTO,petDTO);
		
		return "member/RegisterComplete";
	}

	@RequestMapping("/checkEmail")
	@ResponseBody
	public MemberDTO checkEmail(Model model, @RequestBody MemberDTO memberDTO) {
		return memberMapper.checkEmail(memberDTO);
	}

	// 기본적으로 '로그인'누르면 연결. 그외에 인증처리 안된상태에서 방찾기 등 누르면 로그인으로
	@RequestMapping("/login")
	public String login() {
		return "member/Login";
	}

	// 로그인 성공시
	@RequestMapping("/loginSuccess")
	public String loginSuccess() {
		return "home/Home";
	}

	@RequestMapping("/loginFail")
	public String loginFail(Model model) {
		model.addAttribute("checkFail","로그인을 실패했습니다");
		return "member/Login";
	}
	
	// 아이디찾기
	@RequestMapping("/findId")
	@ResponseBody
	public MemberDTO findId(Model model, @RequestBody MemberDTO memberDTO) {
		return memberMapper.findByName(memberDTO);
	}

	// 패스워드찾기
	@RequestMapping(value = "/findPass", method = RequestMethod.POST)
	public String findPass(MemberDTO memberDTO, RedirectAttributes redirectattr, Errors errors,
			HttpServletResponse response) {
		// 1.이메일형식검사
		new FindPassValidator().validate(memberDTO, errors);
		// 2.이메일형식 안 맞으면 로그인 페이지로 돌아감
		if (errors.hasErrors()) {
			return "member/Login";
		}
		// 3.이메일형식이 맞으면 서비스 인스턴스 생성
		FindpassService service = new FindpassService();

		try {
			// 비번찾기를 위한 excute메서드 실행
			MemberDTO resultDto = service.execute(sqlsession, memberDTO);
			System.out.println("resultDto" + resultDto);
			redirectattr.addFlashAttribute("resultDto", resultDto);
			
			return "redirect:sendpass";
		} catch (Exception e) {
			errors.reject("EmailNotExist");
			
			return "member/Login";
		}
	}

	public void getSession(Authentication auth, HttpSession session, MemberDTO memberDTO) {
		if(auth != null && session.getAttribute("nickname") == null) { 
			SecurityMember sc = (SecurityMember)auth.getPrincipal();
		    memberDTO.setEmail(sc.getUsername());
			  
			MemberDTO nickname = memberMapper.findByEmail(memberDTO); 
			MemberDTO profileImage = memberMapper.getImageDir(sc.getUsername());
			session.setAttribute("nickname", nickname.getNickname());
			session.setAttribute("memberNumber", nickname.getMemberNumber());
			session.setAttribute("gender", nickname.getGender()); //from 민철_meetingAndEvent 조건처리를 위해 사용
		  
			if (profileImage != null) { 
				session.setAttribute("profileImage", profileImage.getImageProfile()); 
			}
		}
	}
	
	public void getSocialSession(Authentication auth, HttpSession session, MemberDTO memberDTO) {
		
		if(auth != null && session.getAttribute("nickname") == null) { 
			auth = SecurityContextHolder.getContext().getAuthentication();
			MemberDTO sc = (MemberDTO)auth.getPrincipal();
			MemberDTO nickname = memberMapper.findByEmail(sc); 
			MemberDTO profileImage = memberMapper.getImageDir(sc.getEmail());
			
			session.setAttribute("email",nickname.getEmail());
			session.setAttribute("name",nickname.getName());
			session.setAttribute("nickname", nickname.getNickname());
			session.setAttribute("memberNumber", nickname.getMemberNumber());
		  
			if (profileImage != null) { 
				session.setAttribute("profileImage",profileImage.getImageProfile()); 
			}
		}
	}
}
