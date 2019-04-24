package com.knockknock.dto.event;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MeetingVDTO implements Serializable{
	/**
	 * 모임카테고리를 담당하는 DTO
	 * from 배민철
	 */
	private static final long serialVersionUID = 1L;
	private int writingNumber; //글번호
	private int memberNumber; //회원번호
	private String title; //글제목
	private Date writeTime; //작성시간
	private Date acceptStartTime; //모임 모집시작시간
	private Date acceptEndTime; //모임 모집마감시간
	private int recruitMaxNumber; //모집인원
	private int recruitNumber; //모집인원
	private int hit; //조회수
	private String simpleIntroduce; //모임 간단소개
	private String detailIntroduce; //모임 상세소개
	private String place; //모임 주소
	private String placeDetail; //모임 세부주소
	private String cancelReason; //취소이유
	private Date meetingStartTime; //모임 시작시간
	private Date meetingEndTime; //모임 종료시간
	private int like; //좋아요
	private String nickname;
	private String gender; //성별
	private String favorite; //관심사
	private String meetingStatus;//모임 승인 상태
	private String image; //이미지

	private List<MultipartFile> meetingImage; //저장용
}
