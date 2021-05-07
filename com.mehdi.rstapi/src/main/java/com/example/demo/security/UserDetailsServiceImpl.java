package com.example.demo.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.entities.UserEntity;
import com.example.demo.reposetory.UserReposetory;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserReposetory userReposetory;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userReposetory.findByEmail(username);
		if(user == null) throw new RuntimeException("user invalide");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		authorities.add(new SimpleGrantedAuthority("USER"));
		System.out.println("22222222");
		return new User(user.getEmail(), user.getEncryptedPassword(), authorities);
	}

}
