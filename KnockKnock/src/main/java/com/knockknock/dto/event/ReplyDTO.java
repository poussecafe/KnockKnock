package com.knockknock.dto.event;

import java.sql.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	private int replyNumber; //댓글 번호(자동증가)
	private int writingNumber; //댓글달릴 글번호
	private int memberNumber; //댓글쓴 회원번호
	private String content; //댓글 내용
	private String nickname; // 댓글 작성자의 닉네임
	private Date writeTime; //댓글 작성시간
	private int parentNumber; //대댓글) 부모댓글 번호
}
