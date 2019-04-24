package com.knockknock.dto.event;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class EventVDTO {
	private int writingNumber; //글번호
	private int memberNumber; //회원번호
	private String title; //글제목
	private int hit; //조회수
	private Date writeTime; //작성시간
	private String content;
	private Date acceptStartTime; //모임 모집시작시간
	private Date acceptEndTime; //모임 모집마감시간
	private int recruitMaxNumber; //모집정원
	private int recruitNumber; //현제인원
	private Date eventStartTime; //모임 시작시간
	private Date eventEndTime; //모임 종료시간
	private String image; //이미지
	private int like; //좋아요
	private String nickname; //닉네임
	private int replyNumber; //댓글번호
	private String replyContent; //댓글내용
	private Date replyWriteDate; //댓글작성시간
	private Date replyWriteTime; //댓글작성시간
	private int parentNumber; //부모댓글 번호
	private String cancelReason; //이벤트 취소 사유
	
	private List<Integer> writingNumberList;
}
