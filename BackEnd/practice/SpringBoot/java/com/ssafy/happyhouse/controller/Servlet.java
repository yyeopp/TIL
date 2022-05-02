//package com.ssafy.happyhouse.controller;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.ssafy.happyhouse.model.MemberDto;
//
//
//@WebServlet("/member")
//public class MemberServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private MemberService memberService = MemberServiceImpl.getMemberService();
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

//		if ("login".equals(act)) {
//			path = doLogin(request, response);
//			request.getRequestDispatcher(path).forward(request, response);
//		} else if ("logout".equals(act)) {
//			path = doLogout(request, response);
//			response.sendRedirect(request.getContextPath() + path);
//		} else if ("regist".equals(act)) {
//			path = doRegist(request, response);
//			request.getRequestDispatcher(path).forward(request, response);
//		} else if ("findpwd".equals(act)) {
//			path = findPassword(request, response);
//			request.getRequestDispatcher(path).forward(request, response);
//		} else if ("update".equals(act)) {
//			path = updateInfo(request, response);
//			request.getRequestDispatcher(path).forward(request, response);
//		} else if ("delete".equals(act)) {
//			path = deleteInfo(request, response);
//			request.getRequestDispatcher(path).forward(request, response);
//		} else if("idcheck".equals(act)) {
//			int cnt = idCheck(request, response);
//			response.getWriter().append(cnt + "");
//		}
//	}
//
//
//	private String doLogin(HttpServletRequest request, HttpServletResponse response) {
//		String id = request.getParameter("id");
//		String password = request.getParameter("password");
//		MemberDto memberDto = null;
//
//		try {
//			memberDto = memberService.getMember(id);
//			if (memberDto != null && password.equals(memberDto.getPassword())) { // 로그인 성공
//				HttpSession session = request.getSession();
//				session.setAttribute("memberInfo", memberDto);
//				return "/index.jsp";
//			} else {
//				request.setAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요.");
//				return "login.jsp";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "로그인 처리중 문제가 발생했습니다.");
//			return "/error/error.jsp";
//		}
//	}
//
//	private String doLogout(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		session.invalidate();
//		return "/index.jsp";
//	}
//
//	private String doRegist(HttpServletRequest request, HttpServletResponse response) {
//		MemberDto memberDto = new MemberDto();
//		try {
//			memberDto.setId(request.getParameter("id"));
//			memberDto.setPassword(request.getParameter("password"));
//			memberDto.setName(request.getParameter("name"));
//			memberDto.setEmail(request.getParameter("email"));
//			memberDto.setAge(Integer.parseInt(request.getParameter("age")));
//			memberService.registerMember(memberDto);
//			request.setAttribute("msg", "회원 가입을 완료했습니다.");
//			return "/index.jsp";
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "회원 가입중 문제가 발생했습니다.");
//			return "/error/error.jsp";
//		}
//	}
//
//	private String findPassword(HttpServletRequest request, HttpServletResponse response) {
//		MemberDto memberDto = new MemberDto();
//		try {
//			memberDto.setId(request.getParameter("id"));
//			memberDto.setName(request.getParameter("name"));
//			memberDto.setEmail(request.getParameter("email"));
//			String pw = memberService.getPassword(memberDto);
//			request.setAttribute("msg", "비밀번호는 " + pw + " 입니다.");
//			return "/index.jsp";
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "비밀번호 찾기 중 문제가 발생했습니다.");
//			return "/error/error.jsp";
//		}
//	}
//
//	private String updateInfo(HttpServletRequest request, HttpServletResponse response) {
//		MemberDto memberDto = new MemberDto();
//		try {
//			HttpSession session = request.getSession();
//			if (session.getAttribute("memberInfo") != null) { // 로그인 세션이 있다면
//				MemberDto loginInfo = (MemberDto) session.getAttribute("memberInfo");
//
//				memberDto.setId(loginInfo.getId());
//				memberDto.setPassword(request.getParameter("password"));
//				memberDto.setName(request.getParameter("name"));
//				memberDto.setEmail(request.getParameter("email"));
//				memberDto.setAge(Integer.parseInt(request.getParameter("age")));
//				memberService.updateMember(memberDto);
//				session.setAttribute("memberInfo", memberDto);
//				request.setAttribute("msg", "회원정보 수정이 완료되었습니다.");
//				return "/index.jsp";
//			} else { // 로그인 세션이 없다면
//				request.setAttribute("msg", "다시 로그인을 해주세요.");
//				return "/login.jsp";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "회원 정보 수정중 문제가 발생했습니다.");
//			return "/error/error.jsp";
//		}
//	}
//
//	private String deleteInfo(HttpServletRequest request, HttpServletResponse response) {
//		// bookmark 테이블의 해당id 정보 전부 삭제 후 members테이블에서 삭제해야 함.
//		try {
//			HttpSession session = request.getSession();
//			if (session.getAttribute("memberInfo") != null) { // 로그인 세션이 있다면
//				MemberDto loginInfo = (MemberDto) session.getAttribute("memberInfo");
//				String id = loginInfo.getId();
//				bookMarkService.deleteId(id); // 북마크 해당 id 삭제
//				memberService.deleteMember(id); // 멤버 삭제
//				session.invalidate();
//				request.setAttribute("msg", "회원 탈퇴가 완료되었습니다.");
//				return "/index.jsp";
//			} else { // 로그인 세션이 없다면
//				request.setAttribute("msg", "다시 로그인을 해주세요.");
//				return "/login.jsp";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "회원 탈퇴 중 문제가 발생했습니다.");
//			return "/error/error.jsp";
//		}
//	}
//
//	private int idCheck(HttpServletRequest request, HttpServletResponse response) {
//		int cnt = 1;
//		String id = request.getParameter("ckid");
//		try {
//			cnt = memberService.idCheck(id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return cnt;
//	}
//}
