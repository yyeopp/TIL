package com.ssafy.ws.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ws.dto.UserDto;
import com.ssafy.ws.model.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	public UserServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDto select(Map<String, String> map) throws Exception{
		return userRepo.select(map);
	}

}
