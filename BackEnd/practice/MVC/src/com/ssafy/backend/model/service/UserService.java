package com.ssafy.backend.model.service;

import com.ssafy.backend.model.UserDto;

public interface UserService {
	
	UserDto login(String id, String pass);
}
