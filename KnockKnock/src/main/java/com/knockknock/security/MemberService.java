package com.knockknock.security;

import java.util.List;

import com.knockknock.dto.branch.BranchDetailVDTO;
import com.knockknock.dto.event.EventVDTO;
import com.knockknock.dto.event.MeetingVDTO;
import com.knockknock.dto.member.LikeBranchDTO;
import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.PetDTO;
import com.knockknock.dto.member.ProfileVDTO;
import com.knockknock.dto.member.VisitDTO;

public interface MemberService{
	//회원가입
	public void register(MemberDTO memberDTO, PetDTO petDTO);
	//추가회원가입(소셜로그인용)
	public void suvRegister(MemberDTO memberDTO, PetDTO petDTO);
	// 내 모임 리스트 - 신청
	public List<MeetingVDTO> getMMLJ(String email);
	// 내 모임 리스트 - 주최
	public List<MeetingVDTO> getMMLM(String email);
	//비밀번호변경(임시비밀번호)
	public void changePassword(MemberDTO memberDTO);
	//비밀번호변경(수정비밀번호)
	public void changeRealPassword(MemberDTO memberDTO);
	//마이페이지-프로필가져오기
	public List<MemberDTO> getProfile(String username);
	//마이페이지-프로필수정
	public void profileUpdate(ProfileVDTO profileVDTO);
	//마이페이지-프로필수정(강아지없을때)
	public void profileUpdate2(ProfileVDTO profileVDTO);
	//마이페이지-프로필사진업데이트
	public void saveImageDir(String finalImage,String username); 
	//마이페이지-프로필사진경로겟
	public MemberDTO getImageDir(String username);
	//겟펫
	public List<MemberDTO> getPet(String username);
	//펫체크
	public ProfileVDTO petCheck(ProfileVDTO profileVDTO); 
	//펫없던사람의 첫 펫 업데이트
	public void firstMyPetUpdate(ProfileVDTO profileVDTO);
	//펫삭제
	public void deletePet(ProfileVDTO profileVDTO);
	// 내 이벤트 리스트
	public List<EventVDTO> getMEL(String email);
//	public List<VisitVDTO> myVisitList(int memberNumber);
	// 내 방문 신청 리스트
	public List<VisitDTO> getMVL(String email);
	// 내 관심 지점 리스트
	public List<BranchDetailVDTO> getMLBL(String email);
	// 내 관심 지점 삭제
	public int deleteLB(int branchNumber, String email);
	// 모임 신청 취소
	public int deleteJM(int writingNumber, String email);
	// 모임 개설 취소
	public void deleteMM(int writingNumber, String email);
	public void cancelMM(MeetingVDTO meetingVDTO, String email);
	public void cancelMM2(MeetingVDTO meetingVDTO, String email);
	// 이벤트 참가 취소 - 각각
	public int deleteJE(int writingNumber, String email);
	// 이벤트 참가 취소 - 체크 박스
	public int checkedDeleteJE(int writingNumber, String email);
	// 방문 신청 취소
	public int deleteV(int writingNumber, String email);
	// 모임 취소 사유 확인
	public MeetingVDTO confirmReason(int writingNumber);
	
	public EventVDTO confirmReasonEvent(int writingNumber);
	
	int getMemberNumber();
}
