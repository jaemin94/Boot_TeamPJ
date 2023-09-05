package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.config.auth.CustomAccessDeniedHandler;
import com.example.demo.config.auth.CustomAuthenticationEntryPoint;
import com.example.demo.config.auth.CustomAuthenticationFailureHandler;
import com.example.demo.config.auth.CustomLoginSuccessHandler;
import com.example.demo.config.auth.CustomLogoutHandler;
import com.example.demo.config.auth.CustomLogoutSuccessHandler;
import com.example.demo.config.auth.PrincipalDetailService;

// security-context.xml 설정 내용

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PrincipalDetailService PrincipalDetailService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
				.csrf().ignoringAntMatchers("/th/3ice/withdraw");// 중간 공격을 맞아주는 설정

		http
			.authorizeRequests()
				.antMatchers("/css/**").permitAll()// 인가 처리
				.antMatchers("/js/**").permitAll()// 인가 처리
				.antMatchers("/image/**").permitAll()// 인가 처리
				.antMatchers("/","/public","/th/3ice/join","/th/3ice/login","/sms/send","/th/3ice/index","/th/3ice/myPage","/th/3ice/update","/th/3ice/withdraw").permitAll()
				.antMatchers("/th/3ice/withdraw").permitAll() 																// hasRole을 사용시 기본적으로 Role_ 이 제공된다
				.antMatchers("/user").hasRole("User")							// Role_User
				.antMatchers("/member").hasRole("Member")						// Role_Member
				.antMatchers("/admin","/product").hasRole("Admin")							// Role_Admin
				.anyRequest().authenticated()
				// 나머지 URL은 모두 인증작업이 완료된 이후 접근가능
				.and()
				.formLogin()													// 로그인이 되지 않은 상태에서 자동으로 로그인 페이지로 리다이렉팅이 된다
				.loginPage("/th/3ice/login").permitAll()											// 커스텀 로그인 페이지 설정
				.successHandler(new CustomLoginSuccessHandler())				// 로그인시 역활에 따른 페이지 설정
				.failureHandler(new CustomAuthenticationFailureHandler())		// 로그인 실패 예외처리

				.and()
	// 로그아웃 처리
				.logout()
				.logoutUrl("/logout")
				.permitAll()
				.addLogoutHandler(new CustomLogoutHandler())					// 로그아웃시 세션초기화
				.logoutSuccessHandler(new CustomLogoutSuccessHandler());		// 로그아웃 성공적으로 되었을경우 기본위치로 페이지 이동

			http
				.exceptionHandling()
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())	// 인증 실패 예외처리
				.accessDeniedHandler(new CustomAccessDeniedHandler());			// 권한 실패 예외처리

//			http
//				.addFilterBefore(null, null);
	}

	// 인증처리 함수
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// auth.userDetailsService : DB 연결할때 사용되는 함수

		auth.userDetailsService(PrincipalDetailService).passwordEncoder(passwordEncoder);


	}



}
