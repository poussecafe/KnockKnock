package com.knockknock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.knockknock.dto.member.MemberDTO;
import com.knockknock.mapper.MemberMapper;

@Service
//UserDetails : 유저 정보를 제공하는 인터페이스
//UserDetailsService : UserDetails를 상속받아 로그인 기능 구현을 돕는 인터페이스/DAO처럼사용됨 /인증된 결과를 여기 담음
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private MemberMapper memberMapper;

	@Override
	//username(email)을 통해, 그 이메일에 해당하는 회원정보를 찾아오는 메서드
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDTO memberDTO = memberMapper.findById(username);
		
		//MEMBER테이블에서 가져온 값이 있으면, 시큐리티 멤버를 호출한다
		if(memberDTO!=null) {
			return new SecurityMember(memberDTO);
		}
		else {
			return null;
		}
	}
}
