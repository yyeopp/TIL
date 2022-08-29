package com.ssafy.backend.model.dao;

import java.sql.SQLException;

import com.ssafy.backend.model.UserDto;

public interface UserDao {
	UserDto login(String id, String pass) throws SQLException;
}
