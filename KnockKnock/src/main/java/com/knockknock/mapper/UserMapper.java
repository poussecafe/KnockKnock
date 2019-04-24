package com.knockknock.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.UserConnectionDTO;

@Mapper
public interface UserMapper {

	public MemberDTO findBySocial(UserConnectionDTO userConnectionDTO);
	public void signUp(UserConnectionDTO userConnectionDTO);
	public MemberDTO save(MemberDTO memberDTO);
	
}
