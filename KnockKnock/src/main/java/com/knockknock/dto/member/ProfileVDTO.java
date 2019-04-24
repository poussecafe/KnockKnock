package com.knockknock.dto.member;

import java.sql.Date;

import lombok.Data;

@Data
public class ProfileVDTO {
	private int memberNumber;
	private String favorite1;
	private String favorite2;
	private String favorite3;
	private String name;
	private String nickname;
	private Date birth;
	private String imageProfile;
	private String grade;
	private int petNumber;
	private String animal;
	private int amount;
	
	private String introduce;
	private String phoneNumber;
}
