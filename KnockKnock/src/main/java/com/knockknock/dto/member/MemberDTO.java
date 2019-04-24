package com.knockknock.dto.member;

import java.sql.Date;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import lombok.Data;

@Data
public class MemberDTO{
	private int memberNumber;
	private String grade;
	private String favorite1;
	private String favorite2;
	private String favorite3;
	private String email;
	private String password;
	private String name;
	private String nickname;
	private Date birth;
	private String gender;
	private String address;
	private String addressDetail;
	private String addressName;
	private String phoneNumber;
	private Date joinTime;
	private Date leaveTime;
	private String imageProfile;
	private String introduce;
	private Integer contractBranchNumber;
	//임시추가(from 성현)
	private String animal;
	
	//소셜용
	public String provider;
	private String providerId;
	public String accessToken;
	public long expireTime;
	
	public void setAccessToken(OAuth2AccessToken accessToken) {
		System.out.println("FacebookUserDetails()의 setAccessToken(accessToken)입니다");
		this.accessToken = accessToken.getValue();
		this.expireTime = accessToken.getExpiration().getTime();
	}
	

   
}