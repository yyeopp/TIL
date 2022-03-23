package com.ssafy.backend.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 이 서블릿이 호출되기 위해서는 url 상에 http://server_ip:port/context_name/main 이 필요하다.
@WebServlet("/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		service(req, resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		switch (action) {
		case "regist":
			doRegist(req, resp);
			break;
		}
	}

	private void doRegist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String isbn = req.getParameter("isbn");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		int price = Integer.parseInt(req.getParameter("price"));
		String desc = req.getParameter("desc");

		req.setAttribute("isbn", isbn);
		req.setAttribute("title", title);
		req.setAttribute("author", author);
		req.setAttribute("price", price);
		req.setAttribute("desc", desc);

		RequestDispatcher disp = req.getRequestDispatcher("/regist_result.jsp");
		disp.forward(req, resp);

	}

}
