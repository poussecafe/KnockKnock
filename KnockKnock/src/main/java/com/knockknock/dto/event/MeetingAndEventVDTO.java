package com.knockknock.dto.event;

import java.sql.Date;

import lombok.Data;

@Data
public class MeetingAndEventVDTO {
	private String image; //이미지 
	private int memberNumber; //회원번호
	private String eventTitle; //이벤트 제목
	private String meetingTitle; //모임 제목
	private Date eventWriteTime; //이벤트 작성일자
	private Date meetingWriteTime; //모임 작성일자
	private Date eventStartTime; //이벤트 시작시간
	private Date eventEndTime; //이벤트 종료시간
	private Date meetingStartTime; //모임 시작일자
	private Date meetingEndTime; //모임 종료일자
}
