package com.knockknock.dto.member;

import com.knockknock.social.FacebookUserDetails;

import lombok.Data;

@Data
public class UserConnectionDTO {
	public String accessToken;
	public String nickname;
	public String name;
	public String email;
	public long expireTime;
	public String profileUrl;
	public String provider;
	public String providerId;
	public int memberNumber;
	
	private UserConnectionDTO() {
		
	}
	
	public UserConnectionDTO(FacebookUserDetails memberDTO) {
		this.accessToken = memberDTO.getAccessToken();
		this.nickname = memberDTO.getNickname();
		this.name = memberDTO.getName();
		this.email = memberDTO.getEmail();
		this.expireTime = memberDTO.getExpireTime();
		this.profileUrl = memberDTO.getProfileUrl();
		this.provider = memberDTO.getProvider();
		this.providerId = memberDTO.getProviderId();
		this.memberNumber = memberDTO.getMemberNumber();
	}
}
