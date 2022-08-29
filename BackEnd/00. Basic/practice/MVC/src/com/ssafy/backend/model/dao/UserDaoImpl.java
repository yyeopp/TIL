package com.ssafy.backend.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ssafy.backend.model.UserDto;
import com.ssafy.util.DBUtil;

public class UserDaoImpl implements UserDao {

	private static UserDao userDao = new UserDaoImpl();

	private UserDaoImpl() {
	}

	public static UserDao getUserDao() {
		return userDao;
	}

	DBUtil dbUtil = DBUtil.getInstance();

	@Override
	public UserDto login(String id, String pass) {
		UserDto userDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select id, name \n");
			sb.append("from user \n");
			sb.append("where id=? and pass=?");
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDto = new UserDto();
				userDto.setId(rs.getString("id"));
				userDto.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return userDto;

	}

}
