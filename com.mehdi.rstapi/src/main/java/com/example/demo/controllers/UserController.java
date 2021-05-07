package com.example.demo.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Requests.UserRequests;
import com.example.demo.Services.UserService;
import com.example.demo.response.UserResponses;
import com.example.demo.shared.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired 
	UserService userService;
	
	
	@GetMapping
	public String getUser() {
		
		return "getuser was called ";
	}
	
	
	@PostMapping
	public UserResponses creatUser(@RequestBody UserRequests userRequest) {
		
		UserDto userDto =new UserDto();
		System.out.println(userRequest.getPassword());
		BeanUtils.copyProperties(userRequest, userDto);
		
		UserDto createUser =   userService.createUser(userDto);

		
		UserResponses userResponse = new UserResponses();
		
		BeanUtils.copyProperties(createUser, userResponse);
		System.out.println(userResponse.getId());
		return userResponse;
	}
	
	@PutMapping
	public String updateUser() {
	
	return "updateuser  was called";
	}
	
	@DeleteMapping
	public String deleteUser() {
	
	return "deleteuser  was called";
	}
	
}
