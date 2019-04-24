package com.knockknock.dto.event;

import lombok.Data;

@Data
public class JoinEventDTO {
	private int writingNumber; //글 번호
	private int memberNumber; //회원번호
}

