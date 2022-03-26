package com.ssafy.backend.model.service;

import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.model.dao.UserDao;
import com.ssafy.backend.model.dao.UserDaoImpl;

public class UserServiceImpl implements UserService{
	
	private static UserService userService = new UserServiceImpl();
	private UserServiceImpl() {}
	public static UserService getUserService() {
		return userService;
	}
	
	UserDao userDao = UserDaoImpl.getUserDao();

	@Override
	public UserDto login(String id, String pass) throws Exception {
		UserDto userDto = userDao.login(id, pass);
		return userDto;
	}

}
