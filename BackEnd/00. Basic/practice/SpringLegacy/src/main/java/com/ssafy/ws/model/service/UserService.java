package com.ssafy.ws.model.service;

import java.util.Map;

import com.ssafy.ws.dto.UserDto;

public interface UserService {

	UserDto select(Map<String, String> map) throws Exception;
	
}
