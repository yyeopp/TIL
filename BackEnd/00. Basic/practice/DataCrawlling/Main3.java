package com;

import java.awt.geom.Point2D;
import java.io.*;
import java.sql.*;

import com.jhlabs.map.proj.Projection;
import com.jhlabs.map.proj.ProjectionFactory;
import com.util.DBUtil;

public class Main3 {
	static DBUtil dbUtil = DBUtil.getInstance();

//	private static String rQ(String s) {
//		return s.substring(1, s.length() - 1);
//	}

	public static void main(String[] args) throws IOException {

		File file = new File("./hospital.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS949"));

		System.out.println(br.readLine());
		while (true) {

			String hospital = br.readLine();
			if (hospital.equals("\"#\"")) {
				break;
			}
			String[] info = hospital.split("\",\"");

			if (!info[7].equals("01")) {
				continue;
			}
			String yadmNm = (info[21]);
			String telno = (info[15]);
			String addr = (info[18]);
			String lng = null;
			String lat = null;

			if (!info[26].equals("")) {
				String[] orgProj = { info[26], info[27] };
				String[] p = projection(orgProj);
				lat = p[1];
				lng = p[0];

			}

			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = dbUtil.getConnection();
				StringBuilder sb = new StringBuilder();
				sb.append("insert into hospital (yadmNm, telno, addr, lat, lng)\n");
				sb.append("values (?,?,?,?,?)");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, yadmNm);
				pstmt.setString(2, telno);
				pstmt.setString(3, addr);
				pstmt.setString(4, lat);
				pstmt.setString(5, lng);
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(yadmNm);
			} finally {
				dbUtil.close(pstmt, conn);
			}

		}

	}

	private static String[] projection(String[] org) {
		String[] proj4 = new String[] { "+proj=tmerc", "+lat_0=38N", "+lon_0=127.00289027777777777776E",
				"+ellps=bessel", "+units=m", "+x_0=200000", "+y_0=500000", "+k=1.0" };

		Projection proj = ProjectionFactory.fromPROJ4Specification(proj4);

		Point2D.Double srcProjec = new Point2D.Double(132, 37);
		Point2D.Double dstProject = proj.transform(srcProjec, new Point2D.Double());

		srcProjec = new Point2D.Double(Double.parseDouble(org[0]), Double.parseDouble(org[1]));
		dstProject = proj.inverseTransform(srcProjec, new Point2D.Double());

		return new String[] { String.valueOf(dstProject.x), String.valueOf(dstProject.y) };
	}

}
