package com.knockknock.security;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.knockknock.dto.branch.BranchDetailVDTO;
import com.knockknock.dto.event.EventVDTO;
import com.knockknock.dto.event.MeetingVDTO;
import com.knockknock.dto.member.LikeBranchDTO;
import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.PetDTO;
import com.knockknock.dto.member.ProfileVDTO;
import com.knockknock.dto.member.VisitDTO;
import com.knockknock.mapper.AdminMapper;
import com.knockknock.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void register(MemberDTO memberDTO, PetDTO petDTO) {
		//3.memberDTO의 패스워드를 인코딩처리한다
		memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
		//4.인코딩 후, memberMapper로 등록을 간다
		memberMapper.register(memberDTO);
		int memberNumber = getMemberNumber();
		memberDTO.setMemberNumber(memberNumber);
		memberMapper.petRegister(memberDTO, petDTO);
	}
	
	@Override
	public void suvRegister(MemberDTO memberDTO, PetDTO petDTO) {
		int memberNumber = getMemberNumber();
		memberDTO.setMemberNumber(memberNumber);
		memberMapper.suvRegister(memberDTO);
		System.out.println(memberDTO.getMemberNumber());
		memberMapper.petRegister(memberDTO, petDTO);
	}
	
	@Override
	public int getMemberNumber() {
		ArrayList<Integer> memberNumber = memberMapper.getMemberNumber();
		int maxMemberNumber = Collections.max(memberNumber);
		return maxMemberNumber;
	}

	//펫체크
	public ProfileVDTO petCheck(ProfileVDTO profileVDTO) {
		return memberMapper.petCheck(profileVDTO);
	}
	
	//펫딜리트
	public void deletePet(ProfileVDTO profileVDTO) {
		memberMapper.deletePet(profileVDTO);
	}
	
	@Override
	public List<MeetingVDTO> getMMLJ(String email) {
		return memberMapper.getMMLJ(email);
	}

	@Override
	public List<MeetingVDTO> getMMLM(String email) {
		return memberMapper.getMMLM(email);
	}
	//임시비번변경
	public void changePassword(MemberDTO memberDTO) {
		int randomInt;
		String randomStr="";
		String tempPassword="";
		Random rnd = new Random();
		
		for(int i=0; i<3; i++) {
			randomInt = (int)(Math.random()*9)+1;
			randomStr = String.valueOf((char) ((int) (rnd.nextInt(26)) + 97));
			tempPassword+=randomInt+randomStr;
		}
		
		memberDTO.setPassword(new BCryptPasswordEncoder().encode(tempPassword));
		
		memberMapper.changePassword(memberDTO);
	}
	
	public void changeRealPassword(MemberDTO memberDTO) {
		memberDTO.setPassword(new BCryptPasswordEncoder().encode(memberDTO.getPassword()));
		memberMapper.changePassword(memberDTO);
	}
	
	public List<MemberDTO> getProfile(String username){
		return memberMapper.getProfile(username);
	}		
	
	public void profileUpdate(ProfileVDTO profileVDTO) {
	 memberMapper.profileUpdate(profileVDTO);
	}
	
	public void profileUpdate2(ProfileVDTO profileVDTO) {
		 memberMapper.profileUpdate2(profileVDTO);
	}
	
	//이미지경로 업로드
	public void saveImageDir(String finalImage, String username) {
		memberMapper.saveImageDir(finalImage, username);
	}
	
	public MemberDTO getImageDir(String username){
		return memberMapper.getImageDir(username);
	}

	public List<EventVDTO> getMEL(String email){
		return memberMapper.getMEL(email);
	}

	@Override
	public List<MemberDTO> getPet(String username) {
		return memberMapper.getPet(username);
	}
	
	public void firstMyPetUpdate(ProfileVDTO profileVDTO) {
		memberMapper.firstMyPetUpdate(profileVDTO);
	}

	public List<VisitDTO> getMVL(String email){
		return memberMapper.getMVL(email);
	}

	@Override
	public int deleteJM(int writingNumber, String email) {
		memberMapper.deleteJM(writingNumber, email);
		return 1;
	}

	@Override
	public void deleteMM(int writingNumber, String email) {
		memberMapper.deleteMM(writingNumber, email);
	}
	
	@Override
	public int deleteJE(int writingNumber, String email) {
		memberMapper.deleteJE(writingNumber, email);
		return 1;
	}
	
	@Override
	public int checkedDeleteJE(int writingNumber, String email) {
		memberMapper.checkedDeleteJE(writingNumber, email);
		return 1;
	}


	@Override
	public int deleteV(int writingNumber, String email) {
		memberMapper.deleteV(writingNumber, email);
		return 1;
	}

	@Override
	public void cancelMM(MeetingVDTO meetingVDTO, String email) {
		memberMapper.cancelMM(meetingVDTO, email);
	}
	
	@Override
	public void cancelMM2(MeetingVDTO meetingVDTO, String email) {
		memberMapper.cancelMM2(meetingVDTO, email);
	}
	
	@Override
	// 내 관심 지점 리스트
	public List<BranchDetailVDTO> getMLBL(String email){
		return memberMapper.getMLBL(email);
	}
	
	@Override
	public int deleteLB(int branchNumber, String email) {
		memberMapper.deleteLB(branchNumber, email);
		return 1;
	}

	@Override
	public MeetingVDTO confirmReason(int writingNumber) {
		 return memberMapper.confirmReason(writingNumber);
	}
	@Override
	public EventVDTO confirmReasonEvent(int writingNumber) {
		return memberMapper.confirmReasonEvent(writingNumber);
	}
}
