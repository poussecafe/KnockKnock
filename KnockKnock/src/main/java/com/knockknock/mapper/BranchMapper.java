package com.knockknock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.knockknock.dto.branch.BranchDetailVDTO;
import com.knockknock.dto.branch.BranchDetailVDTO2;
import com.knockknock.dto.branch.RoomDTO;
import com.knockknock.dto.event.Criteria;
import com.knockknock.dto.member.LikeBranchDTO;
import com.knockknock.dto.member.PetDTO;
import com.knockknock.dto.member.VisitDTO;

@Mapper
public interface BranchMapper {
	
	//메인메뉴 심플방검색
	public List<BranchDetailVDTO> simpleRoomSearchList(BranchDetailVDTO branchDetailVDTO);
	//네비게이션바 방찾기
	public List<BranchDetailVDTO> findingRoomList(BranchDetailVDTO branchDetailVDTO);
	//네비게이션바 관심사로 방찾기
	public List<BranchDetailVDTO> findingCategoryRoomList(BranchDetailVDTO branchDetailVDTO) throws Exception;
	//관심사로 방찾기 페이징 처리
	public int categoryCount(Criteria cri) throws Exception;
	//관심사로 찾기의 방검색
//	public  List<BranchDetailVDTO> categoryRoomSearch(String address, List<String> searchKeyWord) throws Exception;
	//관심사로 찾기의 방검색
	public  List<BranchDetailVDTO> categoryRoomSearch2(BranchDetailVDTO2 branchDetailVDTO2) throws Exception;
	
	//방찾기의 방검색
	public List<BranchDetailVDTO> roomList(BranchDetailVDTO branchDetailVDTO);
	// 지점 상세 정보
	public BranchDetailVDTO getDetail(int branchNumber);
	// 지점 방문 신청
	public void visitBooking(@Param("visitDTO") VisitDTO visitDTO, String email);
	// 관심 지점 등록
	public void likeBranch(int branchNumber, String email);
	
	public List<RoomDTO> getRoomInfo(int branchNumber);

	public List<BranchDetailVDTO> getMemberInfo(int branchNumber);

	public List<PetDTO> getPetInfo(int branchNumber);

	//테마 체크박스
	public List<String> getThemeLists();
	//남 or 여
	public List<String> getIsGender();
	//펫 유무 체크박스
	public List<String> getIspet();
	//주택유형 체크박스
	public List<String> getBranchType();
	
	public LikeBranchDTO getHeartStatus(int branchNumber, String email);

}
