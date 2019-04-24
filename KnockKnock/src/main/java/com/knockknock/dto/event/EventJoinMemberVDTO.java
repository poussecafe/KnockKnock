package com.knockknock.dto.event;

import lombok.Data;

@Data
public class EventJoinMemberVDTO {
	int writingNumber;
	int memberNumber;
	String name;
	String nickname;
	String gender;
	String phoneNumber;
}
