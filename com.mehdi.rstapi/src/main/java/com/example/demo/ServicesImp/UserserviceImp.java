package com.example.demo.ServicesImp;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Services.UserService;
import com.example.demo.entities.UserEntity;
import com.example.demo.reposetory.UserReposetory;
import com.example.demo.shared.UserDto;
import com.example.demo.shared.Utils;

@Service
public class UserserviceImp implements UserService {
	
	@Autowired
	UserReposetory userRepository;
	@Autowired
	Utils util;
	@Autowired
	BCryptPasswordEncoder BCryptePasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
	
		
		
	UserEntity checkUser = userRepository.findByEmail(userDto.getEmail());	
		
		if (checkUser != null) throw new RuntimeException("User Already exists");
	UserEntity userEntiry = new UserEntity();
		
	BeanUtils.copyProperties(userDto, userEntiry);
	System.out.println(userDto.getPassword());
	userEntiry.setEncryptedPassword(BCryptePasswordEncoder.encode(userDto.getPassword()));
	userEntiry.setId(util.generateUserId(32));
	
	
	UserEntity newUser =  userRepository.save(userEntiry);
	newUser.setId(util.generateUserId(32));
	
	UserDto userdto = new UserDto();
	BeanUtils.copyProperties(newUser, userdto);
	
		return userdto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		
		if(user == null) throw new UsernameNotFoundException(email);
		
		return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
	}

}
