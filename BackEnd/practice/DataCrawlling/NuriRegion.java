package com;

import java.io.*;
import java.sql.*;

import com.util.DBUtil;

public class NuriRegion {

	static DBUtil dbUtil = DBUtil.getInstance();

	public static void main(String[] args) throws IOException {

		File file = new File("./seoul.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS949"));

		String read = br.readLine();

		while (!read.equals("#")) {

			String[] data = read.split(",");
//			System.out.println(Arrays.toString(data));

			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = dbUtil.getConnection();
				StringBuilder sb = new StringBuilder();
				sb.append("update region \n");
				sb.append("set regionLat = ?, regionLng=? \n");
				sb.append("where regionCode = ?");

				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setString(1, data[2]);
				pstmt.setString(2, data[3]);
				pstmt.setInt(3, Integer.parseInt(data[0]));

				pstmt.execute();

			} catch (SQLException e) {
				System.out.println("등록 실패: " + data[1] + " @ " + data[2]);
			} finally {
				dbUtil.close(pstmt, conn);
			}
			System.out.println("===================================");

			read = br.readLine();
		}
		br.close();

	}

}
