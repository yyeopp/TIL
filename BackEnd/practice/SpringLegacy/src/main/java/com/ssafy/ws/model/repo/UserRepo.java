package com.ssafy.ws.model.repo;

import java.util.Map;

import com.ssafy.ws.dto.UserDto;

public interface UserRepo {

	UserDto select(Map<String, String> map) throws Exception;
}
