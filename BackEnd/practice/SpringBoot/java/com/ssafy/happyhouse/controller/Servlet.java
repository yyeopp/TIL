//package com.ssafy.controller;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//
//import com.ssafy.dto.BookMarkDto;
//import com.ssafy.dto.MemberDto;
//import com.ssafy.service.BookMarkService;
//import com.ssafy.service.BookMarkServiceImpl;
//
//@WebServlet("/bookmark")
//public class BookMarkServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private BookMarkService bookMarkService = BookMarkServiceImpl.getBookMarkService();
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		doGet(request, response);
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String act = request.getParameter("act");
//		String path = "index.jsp";
//		if ("list".equals(act)) {
//			bookMarkList(request, response);
//		} else if ("insert".equals(act)) {
//			insertBookMark(request, response);
//		} else if ("delete".equals(act)) {
//			deleteBookMark(request, response);
//		}
//	}
//
//	private void bookMarkList(HttpServletRequest request, HttpServletResponse response) {
//		List<BookMarkDto> list = null;
//		try {
//			HttpSession session = request.getSession();
//			if (session.getAttribute("memberInfo") != null) { // 로그인 세션이 있다면
//				MemberDto loginInfo = (MemberDto) session.getAttribute("memberInfo");
//				String id = loginInfo.getId();
//				list = bookMarkService.bookMarkList(id);
//
//				JSONArray jarray = new JSONArray();
//
//				for (BookMarkDto bookMarkDto : list) {
//					JSONObject obj = new JSONObject();
//					obj.put("dongcode", bookMarkDto.getDongCode());
//					obj.put("addr", bookMarkDto.getAddr());
//
//					jarray.add(obj);
//				}
//
//				response.setContentType("text/html; charset=utf-8");
//				response.getWriter().print(jarray);
//			} else { // 로그인 세션이 없다면
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void insertBookMark(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			HttpSession session = request.getSession();
//			if (session.getAttribute("memberInfo") != null) { // 로그인 세션이 있다면
//				MemberDto loginInfo = (MemberDto) session.getAttribute("memberInfo");
//				String id = loginInfo.getId();
//				String dongCode = request.getParameter("dongCode");
//				bookMarkService.insertBookMark(id, dongCode);
//				response.getWriter().append('1'); // 등록 정상
//			} else { // 로그인 세션이 없다면
//				response.getWriter().append('0'); // 로그인 세션이 만료된 경우
//			}
//		}
//		catch (Exception e) {
//			try {
//				response.getWriter().append('2'); // 이미 등록된 관심지역
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		}
//	}
//
//	private void deleteBookMark(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			HttpSession session = request.getSession();
//			if (session.getAttribute("memberInfo") != null) { // 로그인 세션이 있다면
//				MemberDto loginInfo = (MemberDto) session.getAttribute("memberInfo");
//				String id = loginInfo.getId();
//				String dongCode = request.getParameter("dongCode");
//				bookMarkService.deleteBookMark(id, dongCode);
//				response.getWriter().append('1');
//			} else { // 로그인 세션이 없다면
//				response.getWriter().append('0');
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
