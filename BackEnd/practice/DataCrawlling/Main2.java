package com;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.util.DBUtil;

public class Main2 {
	static DBUtil dbUtil = DBUtil.getInstance();

	public static void main(String[] args) throws IOException {

		List<String[]> list = new ArrayList<String[]>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

//		getLocation("경기도 동두천시 동두천로 228");

		try {
			conn = dbUtil.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select lat, lng, addr \n");
			sb.append("from subway \n");
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3) });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}

		for (int i = 0; i < list.size(); i++) {
			String[] s = list.get(i);
			if (!s[0].equals("")) {
				continue;
			} else {
				String[] loc = getLocation(s[2]);
				s[0] = loc[0];
				s[1] = loc[1];
			}

		}

//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(Arrays.toString(list.get(i)));
//		}

		conn = null;
		pstmt = null;
		for (int i = 0; i < list.size(); i++) {
			String[] temp = list.get(i);
			try {
				conn = dbUtil.getConnection();
				StringBuilder sb = new StringBuilder();
				sb.append("update subway \n");
				sb.append("set lat = ?, lng = ? \n");
				sb.append("where addr = ?");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, temp[0]);
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);

				pstmt.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbUtil.close(pstmt, conn);
			}
		}
	}

	private static String[] getLocation(String addr) {
		String[] loc = new String[2];
		String apiURL = "http://api.vworld.kr/req/address";

		try {
			int responseCode = 0;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			String keyword = addr;
			String text_content = URLEncoder.encode(keyword.toString(), "utf-8");
			// String text_content = URLEncoder.encode(keyword.toString());

			// post request
			String postParams = "service=address";
			postParams += "&request=getcoord";
			postParams += "&version=2.0";
			postParams += "&crs=EPSG:4326";
			postParams += "&address=" + text_content;
			postParams += "&arefine=true";
			postParams += "&simple=false";
			postParams += "&format=json";
			postParams += "&type=road";
			postParams += "&errorFormat=json";
			postParams += "&key=FE5001C2-99E6-394A-83E5-7462E8CADFC7";

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			responseCode = con.getResponseCode();
			BufferedReader br;

			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

//			System.out.println("response : " + response);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response.toString());
			JSONObject obj1 = (JSONObject) obj.get("response");
//			System.out.println(obj1.toString());
			JSONObject obj2 = (JSONObject) obj1.get("result");
//			System.out.println(obj2.toString());
			try {
				JSONObject obj3 = (JSONObject) obj2.get("point");
//			System.out.println(obj3.toString());
				String x = (String) obj3.get("x");
				String y = (String) obj3.get("y");
				loc[0] = y;
				loc[1] = x;
			} catch (NullPointerException e) {
				System.out.println(addr);
			}

			br.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loc;
	}

}
