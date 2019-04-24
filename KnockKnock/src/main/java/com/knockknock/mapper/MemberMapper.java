package com.knockknock.mapper;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.knockknock.dto.branch.BranchDetailVDTO;
import com.knockknock.dto.event.EventVDTO;
import com.knockknock.dto.event.MeetingVDTO;
import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.PetDTO;
import com.knockknock.dto.member.ProfileVDTO;
import com.knockknock.dto.member.VisitDTO;

@Mapper
public interface MemberMapper {
	//회원가입
	public void register(MemberDTO memberDTO);
	public void petRegister(@Param("memberDTO") MemberDTO memberDTO, @Param("petDTO") PetDTO petDTO);
	public void suvRegister(MemberDTO memberDTO);
	//찾기관련
	public MemberDTO findById(String id);
	public MemberDTO findByEmail(MemberDTO memberDTO);
	public MemberDTO findByName(MemberDTO memberDTO);
	
	public List<MeetingVDTO> getMMLJ(String email);
	public List<MeetingVDTO> getMMLM(String email);

	public MemberDTO checkEmail(MemberDTO memberDTO);

	public void changePassword(MemberDTO memberDTO);
	//마이페이지
	public List<MemberDTO> getProfile(String username);
	//마이페이지
	public List<MemberDTO> getPet(String username);
	//마이페이지-프로필수정
	public void profileUpdate(ProfileVDTO profileVDTO);
	//마이페이지-프로필수정(강아지없을때)
	public void profileUpdate2(ProfileVDTO profileVDTO);
	//마이페이지-펫체크
	public ProfileVDTO petCheck(ProfileVDTO profileVDTO);
	//마이페이지-퍼스트마이펫
	public void firstMyPetUpdate(ProfileVDTO profileVDTO);
	//마이페이지-딜리트펫
	public void deletePet(ProfileVDTO profileVDTO);
	//마이페이지-프로필사진겟
	public MemberDTO getImageDir(String username);
	
	public List<EventVDTO> getMEL(String email);
	
	public List<VisitDTO> getMVL(String email);

	public int deleteJM(int writingNumber, String email);
	
	public int checkedDeleteJE(int writingNumber, String email);
	
	public int deleteMM(int writingNumber, String email);
	
	public int deleteJE(int writingNumber, String email);
	
	public int deleteV(int writingNumber, String email);
	
	public void saveImageDir(String finalImage, String username);
	
	public ArrayList<Integer> getMemberNumber();
	
	public void cancelMM(@Param("meetingVDTO") MeetingVDTO meetingVDTO, String email);
	public void cancelMM2(@Param("meetingVDTO") MeetingVDTO meetingVDTO, String email);
	
	// 내 관심 지점 리스트
	public List<BranchDetailVDTO> getMLBL(String email);

	public int deleteLB(int branchNumber, String email);
	
	public MeetingVDTO confirmReason(int writingNumber);
	
	public EventVDTO confirmReasonEvent(int writingNumber);

}
