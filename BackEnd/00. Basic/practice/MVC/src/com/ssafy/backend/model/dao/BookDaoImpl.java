package com.ssafy.backend.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.backend.model.BookDto;
import com.ssafy.util.DBUtil;

public class BookDaoImpl implements BookDao {

	private static BookDao bookDao = new BookDaoImpl();

	private BookDaoImpl() {
	}

	public static BookDao getBookDao() {
		return bookDao;
	}

	DBUtil dbUtil = DBUtil.getInstance();

	public boolean verifyBook(BookDto bookDto) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select isbn \n");
			sb.append("from book \n");
			sb.append("where isbn = ?");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, bookDto.getIsbn());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			dbUtil.close(pstmt, conn);
		}

	}

	@Override
	public String registerBook(BookDto bookDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("insert into book (isbn, title, author, price, `desc`) \n");
			sb.append("values (?,?,?,?,?)");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, bookDto.getIsbn());
			pstmt.setString(2, bookDto.getTitle());
			pstmt.setString(3, bookDto.getAuthor());
			pstmt.setInt(4, bookDto.getPrice());
			pstmt.setString(5, bookDto.getDesc());
			pstmt.executeUpdate();

			return "/book?action=result";
		} catch (SQLException e) {

			e.printStackTrace();
			return "/error/error.jsp";
		} finally {

			dbUtil.close(pstmt, conn);
		}

	}

	@Override
	public List<BookDto> getBook() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BookDto> list = new ArrayList<BookDto>();
		ResultSet rs = null;

		try {
			conn = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select isbn, title, author, price, `desc` \n");
			sb.append("from book \n");
			sb.append("order by isbn");
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookDto bookDto = new BookDto();
				bookDto.setIsbn(rs.getString("isbn"));
				bookDto.setTitle(rs.getString("title"));
				bookDto.setAuthor(rs.getString("author"));
				bookDto.setPrice(rs.getInt("price"));
				bookDto.setDesc(rs.getString("desc"));
				list.add(bookDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			dbUtil.close(pstmt, conn);
		}
		return list;
	}

}
