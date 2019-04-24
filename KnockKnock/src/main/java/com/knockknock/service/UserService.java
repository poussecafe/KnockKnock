package com.knockknock.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.UserConnectionDTO;
import com.knockknock.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	@Autowired
    private final UserMapper userMapper;

    public void signUp(UserConnectionDTO userConnectionDTO) {
         userMapper.signUp(userConnectionDTO);
    }

    public MemberDTO findBySocial(UserConnectionDTO userConnectionDTO) {
        final MemberDTO memberDTO = userMapper.findBySocial(userConnectionDTO);
        return memberDTO;
    }

    public boolean isExistUser(UserConnectionDTO userConnectionDTO) {
        final MemberDTO memberDTO = userMapper.findBySocial(userConnectionDTO);
        return (memberDTO != null);
    }
}
