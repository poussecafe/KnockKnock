package com.knockknock.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.knockknock.controller.LoginFailController;
import com.knockknock.security.CustomUserDetailsService;
import com.knockknock.social.FacebookOAuth2ClientAuthenticationProcessingFilter;
import com.knockknock.social.SocialService;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	// 소셜로그인 관련
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	@Autowired
	SocialService socialService;

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 기존설정
		http.authorizeRequests()
				.antMatchers("/login").anonymous()
				// 매치 : static 이하
				.antMatchers("/contactform/**", "/css/**", "/images/**", "/img/**", "/js/**", "/lib/**", "/vendor/**",
						"static/**")
				.permitAll()
				// 매치 : BranchController
				.antMatchers("/roomSearch", "/categoryRoomSearch", "/roomDetailView/**").permitAll()
				// 매치 : MainController
				.antMatchers("/", "/simpleRoomSearch", "/findingRoom", "/findingCategoryRoom", "/meetingAndEventMain",
						"/toSharingGuide", "/toFAQ")
				.permitAll()
				// 매치 : MeetingAndEventController
				.antMatchers("/meetingList", "/wrtingBoard", "/meetingView", "/meetingModify", "/eventList", "/eventView")
				.permitAll()
				// 매치 : ReplyController
				.antMatchers("/meetingReplyList").permitAll() // from 민철 추가
				// 매치 : ReviewController
				.antMatchers("/reviewList").permitAll()
				// 매치 : MemberController
				.antMatchers("/register", "/create", "/checkEmail", "/findId", "/findPass", "/login/facebook")
				.permitAll()
				// 매치 : MailController
				.antMatchers("/sendpass").permitAll()
				// 매치 : 방문신청 인증 필요
				.antMatchers("/visitBooking").authenticated()
				.anyRequest().authenticated();
				
		http
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureHandler(new LoginFailController());
		http
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login").deleteCookies("JSESSIONID");

		http.authorizeRequests().antMatchers("/**")
		.permitAll().and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);

		//이 코드가 없으면 CSRF 토큰을 발급하지 않는다.CSRF 위험에 노출.(개발자도구->네트워크->어플리케이션에서 확인)
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		
	}

	// 로그인 처리 시 인증에 대한 처리
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//eraseCredentials() : 민감한 정보를 유저로부터 삭제하기 위한 메서드 
		auth.eraseCredentials(false).userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
	}

	//회원가입시 비밀번호 인코딩을 위한 패스워드 인코더 Bean
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	//@ConfigurationProperties는 yaml이나 properties를 탐색하게 도와주는 어노테이션
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }

	//페북빈
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	
	//소셜
	private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(facebook(), new FacebookOAuth2ClientAuthenticationProcessingFilter(socialService)));
        filter.setFilters(filters);
        return filter;
    } 
	
	//OAuth2ClientAuthenticationProcessingFilter는 권한써버로부터 인증토큰을 얻기 위해 사용되는 필터이다.
    private Filter ssoFilter(ClientResources client, OAuth2ClientAuthenticationProcessingFilter filter) {
        //Rest template that is able to make OAuth2-authenticated REST requests with the credentials of the provided resource.
    	OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(restTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
        filter.setTokenServices(tokenServices);
        tokenServices.setRestTemplate(restTemplate);
        return filter;
    }
}
