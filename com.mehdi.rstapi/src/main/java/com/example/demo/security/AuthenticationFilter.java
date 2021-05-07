package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.management.RuntimeErrorException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Requests.UserLogInRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationrManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationrManager ) {
		this.authenticationrManager =authenticationrManager;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			
			UserLogInRequest creds = new ObjectMapper().readValue(request.getInputStream(), UserLogInRequest.class);

			
			return authenticationrManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>())
					);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	protected void SuccessfulAuthentication(HttpServletRequest req,HttpServletResponse rep ,FilterChain chain, Authentication auth)
	throws IOException, SerialException
	{
		String userName = ((User)auth.getPrincipal()).getUsername();
		//User user = 
		String token = Jwts.builder()
						.setSubject(userName)
						.setExpiration(new Date(System.currentTimeMillis()+SecurityConstant.EXPIRATION_TIME))
						.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512,SecurityConstant.TOKEN_SECRET).compact();
	
	rep.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX+token);
	}
	
	
}
