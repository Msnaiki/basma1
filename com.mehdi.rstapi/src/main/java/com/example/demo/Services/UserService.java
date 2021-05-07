package com.example.demo.Services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.shared.UserDto;

public interface UserService extends UserDetailsService {
UserDto createUser(UserDto userDto);
}
