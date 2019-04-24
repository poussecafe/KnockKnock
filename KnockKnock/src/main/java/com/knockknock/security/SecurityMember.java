package com.knockknock.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.knockknock.dto.member.MemberDTO;
//User : UserDetails를 상속받는 놈
public class SecurityMember extends User implements UserDetails{

	private static final long serialVersionUID = 1L;
	//생성자. memberDTO의 정보들을 super를 통해 User클래스쪽으로 넘겨 담는다.
	public SecurityMember(MemberDTO memberDTO) {
		super(memberDTO.getEmail(),memberDTO.getPassword(),makeAuth(memberDTO));
	}
	//회원grade처리 메서드.리스트형 컬렉션 객체에 그레이드를 담는다.
	private static Collection<GrantedAuthority> makeAuth(MemberDTO memberDTO) {
		Collection<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(memberDTO.getGrade()));
		
		return list;
	}
}
