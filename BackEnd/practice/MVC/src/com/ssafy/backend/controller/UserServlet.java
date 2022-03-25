package com.ssafy.backend.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.model.service.UserService;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = UserServiceImpl.getMemberService();
	// DAO와 controlloer 사이에 service를 항상 두자. 객체 가져오기

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String act = request.getParameter("act");
		String path = "/index.jsp";
		if ("mvregister".equals(act)) {
			response.sendRedirect(request.getContextPath() + "/user/join.jsp");
		} else if ("register".equals(act)) {
			path = registerMember(request, response);
			request.getRequestDispatcher(path).forward(request, response);
			// 가져온 값 (path, error)이 있으므로 forward로 보낸다.

		} else if ("idcheck".equals(act)) {
			int cnt = idCheck(request, response);
			response.getWriter().append(cnt + "");
			// 단순 check 여부만 확인이므로 forward까지 필요없다.

		} else if ("mvlogin".equals(act)) {
			response.sendRedirect(request.getContextPath() + "/user/login.jsp");
		} else if ("login".equals(act)) {
			path = loginMember(request, response);
			request.getRequestDispatcher(path).forward(request, response);
		} else if ("logout".equals(act)) {
			path = loginOutMember(request, response);
			response.sendRedirect(request.getContextPath() + path);
		}
	}

	private int idCheck(HttpServletRequest request, HttpServletResponse response) {
		int cnt = 1;
		// 일단 막자. 그래야 에러 떴을 때 가입되는걸 막는다.
		String id = request.getParameter("ckid");
		cnt = userService.idCheck(id);

		return cnt;
	}

	private String registerMember(HttpServletRequest request, HttpServletResponse response) {
		UserDto userDto = new UserDto();
		memberDto.setUserName(request.getParameter("username"));
		memberDto.setUserId(request.getParameter("userid"));
		memberDto.setUserPwd(request.getParameter("userpwd"));
		memberDto.setEmail(request.getParameter("emailid") + "@" + request.getParameter("emaildomain"));
		try {
			memberService.registerMember(memberDto);
			// 여기까지 왔으면 회원가입이 성공한 것.
			return "/user/login.jsp";
		} catch (Exception e) {
			// 앞에서 exception을 죄다 throw했다.

			e.printStackTrace();
			request.setAttribute("msg", "회원 가입 중 문제가 발생했습니다.");
			return "/error/error.jsp";
		}
	}

	private String loginMember(HttpServletRequest request, HttpServletResponse response) {
		UserDto userDto = null;

		String id = request.getParameter("userid");
		String pass = request.getParameter("userpwd");

		try {
			memberDto = memberService.login(id, pass);
			if (memberDto != null) { // 로그인 성공, session 활용하기
				HttpSession session = request.getSession(); // 기본객체가 없음
				session.setAttribute("userInfo", memberDto);

				// 아이디 저장을 설정한 경우 cookie setting을 시작
				String idsv = request.getParameter("idsave");
				if ("saveok".equals(idsv)) { // 체크가 있었던 경우

					Cookie cookie = new Cookie("loginid", id); // name, value
					cookie.setMaxAge(60 * 60 * 24 * 365 * 20); // 초 단위.
					cookie.setPath(request.getContextPath()); // 사이트 최상단에 path 지정
					response.addCookie(cookie); // 만들었으면 담아주는 작업까지!
					// response로 보냈으니 client에 저장될 수 있다.
					
					return "/index.jsp";
					
					
				} else {	// 아이디 저장 체크를 뺀 경우
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (int i = 0; i < cookies.length; i++) {
							if (cookies[i].getName().equals("loginid")) {
								cookies[i].setMaxAge(0); // cookie를 삭제하는 메서드는 따로 없으므로
								// 수명을 0으로 줘버리는 것.
								response.addCookie(cookies[i]); // 브라우저에게 response로 알려주기
								break;
							}
						}
					}
					
					return "";
				}
			} else {// 로그인 실패
				request.setAttribute("msg", "아이디, 비번 확인");
				return "/user/login.jsp";
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "로그인 처리중 문제 발생!!");
			return "/error/error.jsp";
		}

	}

	private String loginOutMember(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		// 현재 시점의 session을 불러와서

//		session.setAttribute("userInfo", null);
//		session.removeAttribute("userInfo");
		// 지워버리기. 근데 이건 user 정보만 지우는 거고 장바구니 이런건 유지 가능

		session.invalidate();
		// 이건 세션을 싹 비워버리는 것. 위랑 구분 필요

		return "/index.jsp";
	}

}
