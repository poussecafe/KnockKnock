package com.knockknock.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;

@Controller
public class LoginFailController implements AuthenticationFailureHandler{
	
	
	String failMessage;
	
	public LoginFailController() {

	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String loginFail = request.getParameter("username");
		
		this.failMessage = "로그인 정보가 일치하지 않습니다.";
		
		request.setAttribute("loginFail", loginFail);
		request.setAttribute("fail", failMessage);
		
		request.getRequestDispatcher("/login").forward(request, response);
	}
}
