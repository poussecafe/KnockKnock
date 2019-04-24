package com.knockknock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knockknock.dto.branch.BranchDetailVDTO;
import com.knockknock.dto.branch.BranchDetailVDTO2;
import com.knockknock.dto.branch.RoomDTO;
import com.knockknock.dto.member.LikeBranchDTO;
import com.knockknock.dto.member.PetDTO;
import com.knockknock.dto.member.VisitDTO;
import com.knockknock.mapper.BranchMapper;


@Service
public class BranchService {
	
	@Autowired
	public BranchMapper branchMapper;

	// 네비게이션바 방찾기검색
	public List<BranchDetailVDTO> findingRoomList(BranchDetailVDTO branchDetailVDTO) {
		return branchMapper.findingRoomList(branchDetailVDTO);
	}

	// 메인화면 심플방검색
	public List<BranchDetailVDTO> simpleRoomSearchList(BranchDetailVDTO branchDetailVDTO) {
		return branchMapper.simpleRoomSearchList(branchDetailVDTO);
	}

	// '방찾기'에서 방검색
	public List<BranchDetailVDTO> roomList(BranchDetailVDTO branchDetailVDTO) {
		return branchMapper.roomList(branchDetailVDTO);
	}

	//네비게이션바 관심사로 방찾기검색
	public List<BranchDetailVDTO> findingCategoryRoomList(BranchDetailVDTO branchDetailVDTO) throws Exception{
		return branchMapper.findingCategoryRoomList(branchDetailVDTO);
	}
	
	//관심사로 찾기의 방검색
	public List<BranchDetailVDTO> categoryRoomSearch(BranchDetailVDTO2 branchDetailVDTO2) throws
		Exception{ 
			List<BranchDetailVDTO> searchAndCheckbox=branchMapper.categoryRoomSearch2(branchDetailVDTO2);

		 return  searchAndCheckbox;
		 }
	
	// 지점 상세 정보 - 상세
	public BranchDetailVDTO getDetail(int branchNumber) {
		return branchMapper.getDetail(branchNumber);
	}

	// 지점 상세 정보 - 방
	public List<RoomDTO> getRoomInfo(int branchNumber) {
		return branchMapper.getRoomInfo(branchNumber);
	}

	// 지점 상세 정보 - 입주민
	public List<BranchDetailVDTO> getMemberInfo(int branchNumber) {
		return branchMapper.getMemberInfo(branchNumber);
	}
	
	// 지점 상세 정보 - 반려 동물
	public List<PetDTO> getPetInfo(int branchNumber){
		return branchMapper.getPetInfo(branchNumber);
	}
	
	// 방문 신청
	 public void visitBooking(VisitDTO visitDTO, String email) {
		 branchMapper.visitBooking(visitDTO, email);
	}
	 
	// 관심 지점 등록
	 public void likeBranch(int branchNumber, String email) {
		 branchMapper.likeBranch(branchNumber, email);
	 }

	//테마 검색
	public List<String> getThemeLists(){
		return branchMapper.getThemeLists();
	}
	//남 or 여
	public List<String> getIsGender() {
		return branchMapper.getIsGender();
	}
	//반려동물 유무
	public List<String> getIspet() {
		return branchMapper.getIspet();
	}
	//주택유형 분류
	public List<String> getBranchType() {
		return branchMapper.getBranchType();
	}
	
	public LikeBranchDTO getHeartStatus(int branchNumber, String email) {
		return branchMapper.getHeartStatus(branchNumber, email);
	}

	
}
