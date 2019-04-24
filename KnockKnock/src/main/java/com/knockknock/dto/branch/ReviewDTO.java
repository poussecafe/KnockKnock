package com.knockknock.dto.branch;

import java.sql.Date;

import lombok.Data;

@Data
public class ReviewDTO {
	
	private int writingNumber; // 리뷰 글 번호
	private int branchNumber; // 리뷰가 달릴 지점 번호
	
	private int memberNumber; // 리뷰 작성자의 회원넘버
	private String nickname; // 리뷰 작성자의 닉네임(추가!!!!!!!!!!!!!!!!!!!)
	private String content; // 리뷰 내용
	private Date writeTime; //리뷰 작성 시간
	private String image; // 리뷰 첨부 이미지
}
