package com.knockknock.social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.UserConnectionDTO;
import com.knockknock.mapper.MemberMapper;
import com.knockknock.security.CustomUserDetailsService;
import com.knockknock.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SocialService{

	@Autowired
    private final UserService userService;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	MemberMapper memberMapper;
	
    public UsernamePasswordAuthenticationToken doAuthentication(UserConnectionDTO userConnectionDTO) {
    	
        if(userService.isExistUser(userConnectionDTO)){
            // 기존 회원일 경우에는 데이터베이스에서 조회해서 인증 처리
            final MemberDTO memberDTO = userService.findBySocial(userConnectionDTO);
            return setAuthenticationToken(memberDTO);
        }else{
            // 새 회원일 경우 회원가입 이후 인증 처리
        	userService.signUp(userConnectionDTO);
            final MemberDTO memberDTO = userService.findBySocial(userConnectionDTO);

            return setAuthenticationToken(memberDTO);
        }
    }

    private UsernamePasswordAuthenticationToken setAuthenticationToken(MemberDTO memberDTO){
        return new UsernamePasswordAuthenticationToken(memberDTO, null, getAuthorities(memberDTO));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(MemberDTO memberDTO){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(memberDTO.getGrade()));
        return authorities;
    }
}
