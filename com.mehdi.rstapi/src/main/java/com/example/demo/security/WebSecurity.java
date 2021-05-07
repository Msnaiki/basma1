package com.example.demo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.Services.UserService;
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	private final UserService userdetailsService;
	private final BCryptPasswordEncoder bCryptePasswordEncoder;
	
	public WebSecurity(UserService userdetailsService,BCryptPasswordEncoder bCryptePasswordEncoder ) {
		this.userdetailsService = userdetailsService;
		this.bCryptePasswordEncoder=bCryptePasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and()
		.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST ,SecurityConstant.SIGN_UP_URL).permitAll().anyRequest().authenticated().and().addFilter(new AuthenticationFilter(authenticationManager()));
	}
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userdetailsService).passwordEncoder(bCryptePasswordEncoder);
	}  
}
