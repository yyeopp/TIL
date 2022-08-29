package com.ssafy.backend.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.model.service.UserService;
import com.ssafy.backend.model.service.UserServiceImpl;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = UserServiceImpl.getUserService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String act = request.getParameter("action");
		String path = "/index.jsp";
//		if ("mvregister".equals(act)) {
//			response.sendRedirect(request.getContextPath() + "/user/join.jsp");
//		} else if ("register".equals(act)) {
//			path = registerMember(request, response);
//			request.getRequestDispatcher(path).forward(request, response);
//			// 가져온 값 (path, error)이 있으므로 forward로 보낸다.

		if ("login".equals(act)) {
			path = login(request, response);
			request.getRequestDispatcher(path).forward(request, response);
		} else if ("logout".equals(act)) {
			path = logout(request, response);
			response.sendRedirect(request.getContextPath() + path);
		} else if ("mvlogin".equals(act)) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

	}

	private String login(HttpServletRequest request, HttpServletResponse response) {
		UserDto userDto = null;

		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		System.out.println(id);
		System.out.println(pass);

		try {
			userDto = userService.login(id, pass);
			System.out.println(userDto);

			if (userDto != null) { 
				HttpSession session = request.getSession(); 
				session.setAttribute("userInfo", userDto);
				return "/index.jsp";

			} else {
				request.setAttribute("msg", "아이디 혹은 비밀번호를 확인해주세요.");
				return "/error/error.jsp";
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "로그인 처리중 문제 발생!!");
			return "/error/error.jsp";
		}

	}

	private String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		session.invalidate();

		return "/index.jsp";
	}

}
