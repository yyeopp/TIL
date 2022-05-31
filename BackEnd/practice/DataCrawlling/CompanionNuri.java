package com;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.sql.*;

import org.json.simple.*;

import com.util.DBUtil;

public class CompanionNuri {

	static DBUtil dbUtil = DBUtil.getInstance();

	static class ProjData {

		String addr;
		String roadAddr;
		String lat;
		String lng;
		int regionCode;

		public ProjData(String addr, String roadAddr, String lat, String lng, int regionCode) {
			this.addr = addr;
			this.roadAddr = roadAddr;
			this.lat = lat;
			this.lng = lng;
			this.regionCode = regionCode;
		}

		@Override
		public String toString() {
			return "ProjData [addr=" + addr + ", roadAddr=" + roadAddr + ", lat=" + lat + ", lng=" + lng
					+ ", regionCode=" + regionCode + "]";
		}

	}

	public static void main(String[] args) throws IOException {

		File file = new File("./반려누리.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS949"));

		String read = br.readLine();

		while (!read.equals("#")) {

			String[] data = read.split(",");
//			System.out.println(data[2]);
			ProjData proj = getDataFromKakao(data[2]);

			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = dbUtil.getConnection();
				StringBuilder sb = new StringBuilder();
				sb.append(
						"insert into location(locListId, locName, locAddr, locRoadAddr, locTel, locLat, locLng, locRegionCode) \n");
				sb.append("values (?,?,?,?,?,?,?,?)");
				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setInt(1, Integer.parseInt(data[0]));
				pstmt.setString(2, data[1]);

				try {
					pstmt.setString(3, proj.addr);
				} catch (NullPointerException e) {
				}

				try {
					pstmt.setString(4, proj.roadAddr);
				} catch (NullPointerException e) {
				}
				try {
					pstmt.setString(5, data[3]);
				} catch (ArrayIndexOutOfBoundsException e) {
					pstmt.setString(5, null);
				}

				try {
					pstmt.setString(6, proj.lat);
					pstmt.setString(7, proj.lng);
				} catch (NullPointerException e) {
				}

				try {
					pstmt.setInt(8, proj.regionCode);
				} catch (NullPointerException e) {
				}

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

	private static ProjData getDataFromKakao(String houseAddress) {

		ProjData data = null;

		String restApiKey = "443210c7d620e6d00f9c777d20abfb73";

		HttpURLConnection conn = null;
		StringBuffer response = new StringBuffer();

		String auth = "KakaoAK " + restApiKey;
		String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?&analyze_type=exact&query=";

		try {
			apiUrl += URLEncoder.encode(houseAddress, "UTF-8");

			URL kakaourl = new URL(apiUrl);

			conn = (HttpURLConnection) kakaourl.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-Requested-With", "curl");
			conn.setRequestProperty("Authorization", auth);

			conn.setDoOutput(true);

			int responseCode = conn.getResponseCode();
			if (responseCode == 400) {
				System.out.println("400:: 해당 명령을 실행할 수 없음");
			} else if (responseCode == 401) {
				System.out.println("401:: Authorization가 잘못됨");
			} else if (responseCode == 500) {
				System.out.println("500:: 서버 에러, 문의 필요");
			} else {

				Charset charset = Charset.forName("UTF-8");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

				String inputLine;

				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}

				String addr = "";
				String roadAddr = "";
				String lat = ""; // 37.xx
				String lng = ""; // 127.xx
				int regionCode = 0; // 법정동코드

				JSONObject jObj = (JSONObject) JSONValue.parse(response.toString());
				JSONObject meta = (JSONObject) jObj.get("meta");
				long size = (long) meta.get("total_count");

				if (size > 0) {
					JSONArray jArray = (JSONArray) jObj.get("documents");
//					System.out.println(jArray.toString());

					JSONObject subJobj = (JSONObject) jArray.get(0);

					// 도로명 주소
					JSONObject roadAddress = (JSONObject) subJobj.get("road_address");
					// 기존 주소
					JSONObject address = (JSONObject) subJobj.get("address");

					if (address != null) {
						addr = (String) address.get("address_name");
						String reg = (String) address.get("b_code");
						regionCode = Integer.parseInt(reg.substring(0, 5));
					} else {
						addr = (String) subJobj.get("address_name");
					}

					if (roadAddress == null) {
						lng = (String) address.get("x");
						lat = (String) address.get("y");
					} else {
						roadAddr = (String) roadAddress.get("address_name");
						lng = (String) roadAddress.get("x");
						lat = (String) roadAddress.get("y");
					}

//					// 만약 빈값일 경우
					if (lng.equals("") || lng == null || lat.equals("") || lat == null) {
						// 사이즈가 0보다 크므로 다음 객체로부터 주소와 lat, lng를 받아온다.
						subJobj = (JSONObject) jArray.get(1);
						subJobj = (JSONObject) subJobj.get("address_name");
						roadAddress = (JSONObject) subJobj.get("road_address");
						lng = (String) subJobj.get("x");
						lat = (String) subJobj.get("y");
						addr = (String) subJobj.get("address_name");
						roadAddr = (String) roadAddress.get("address_name");
						String reg = (String) address.get("b_code");
						regionCode = Integer.parseInt(reg.substring(0, 5));
					}
					// 반환할 객체 생성.
					data = new ProjData(addr, roadAddr, lat, lng, regionCode);

				} else {
					System.out.println("유사주소로 ㄱㄱ: " + houseAddress);
					data = getSimilarDataFromKakao(houseAddress);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	private static ProjData getSimilarDataFromKakao(String houseAddress) {
		ProjData data = null;

		String restApiKey = "443210c7d620e6d00f9c777d20abfb73";

		HttpURLConnection conn = null;
		StringBuffer response = new StringBuffer();

		String auth = "KakaoAK " + restApiKey;
		String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?&analyze_type=similar&query=";

		try {
			apiUrl += URLEncoder.encode(houseAddress, "UTF-8");

			URL kakaourl = new URL(apiUrl);

			conn = (HttpURLConnection) kakaourl.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-Requested-With", "curl");
			conn.setRequestProperty("Authorization", auth);

			conn.setDoOutput(true);

			int responseCode = conn.getResponseCode();
			if (responseCode == 400) {
				System.out.println("400:: 해당 명령을 실행할 수 없음");
			} else if (responseCode == 401) {
				System.out.println("401:: Authorization가 잘못됨");
			} else if (responseCode == 500) {
				System.out.println("500:: 서버 에러, 문의 필요");
			} else {

				Charset charset = Charset.forName("UTF-8");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

				String inputLine;

				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}

				String addr = "";
				String roadAddr = "";
				String lat = "";
				String lng = "";
				int regionCode = 0;

				JSONObject jObj = (JSONObject) JSONValue.parse(response.toString());
				JSONObject meta = (JSONObject) jObj.get("meta");
				long size = (long) meta.get("total_count");
				if (size > 0) {
					JSONArray jArray = (JSONArray) jObj.get("documents");
					JSONObject subJobj = (JSONObject) jArray.get(0);

					JSONObject roadAddress = (JSONObject) subJobj.get("road_address");
					JSONObject address = (JSONObject) subJobj.get("address");

					if (address != null) {
						addr = (String) address.get("address_name");
						String reg = (String) address.get("b_code");
						regionCode = Integer.parseInt(reg.substring(0, 5));
					} else {
						addr = (String) subJobj.get("address_name");
					}

					if (roadAddress == null) {
						lng = (String) address.get("x");
						lat = (String) address.get("y");
					} else {
						roadAddr = (String) roadAddress.get("address_name");
						lng = (String) roadAddress.get("x");
						lat = (String) roadAddress.get("y");
					}
//
					if (lng.equals("") || lng == null || lat.equals("") || lat == null) {
						subJobj = (JSONObject) jArray.get(1);
						subJobj = (JSONObject) subJobj.get("address_name");
						roadAddress = (JSONObject) subJobj.get("road_address");
						lng = (String) subJobj.get("x");
						lat = (String) subJobj.get("y");
						addr = (String) subJobj.get("address_name");
						roadAddr = (String) roadAddress.get("address_name");
						String reg = (String) address.get("b_code");
						regionCode = Integer.parseInt(reg.substring(0, 5));
					}
					data = new ProjData(addr, roadAddr, lat, lng, regionCode);

				} else {
					System.out.println("끝끝내 못찾음 " + houseAddress);
					System.out.println(houseAddress);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

}
