package com.ssafy.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.backend.model.BookDto;
import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.model.service.BookService;
import com.ssafy.backend.model.service.BookServiceImpl;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	BookService bookService = BookServiceImpl.getBookService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String act = req.getParameter("action");
		String path = "/index.jsp";
		if ("regist".equals(act)) {
			path = registerBook(req, resp);
			req.getRequestDispatcher(path).forward(req, resp);
		} else if ("result".equals(act)) {
			path = getBook(req, resp);
			req.getRequestDispatcher(path).forward(req, resp);
		} else if ("mvregist".equals(act)) {
			System.out.println("mvregist");
			resp.sendRedirect(req.getContextPath() + "/regist.jsp");
		}
	}

	private String getBook(HttpServletRequest req, HttpServletResponse resp) {

		try {
			List<BookDto> list = bookService.getBook();
			req.setAttribute("books", list);
			return "/regist_result.jsp";

		} catch (Exception e) {

			e.printStackTrace();
			return "/index.jsp";
		}

	}

	private String registerBook(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userInfo");

		if (userDto != null) {

			if ((req.getParameter("isbn").equals("")) || (req.getParameter("title").equals(""))
					|| (req.getParameter("author").equals("")) || (req.getParameter("price").equals(""))
					|| (req.getParameter("desc").equals(""))) {
				return "/regist.jsp";
			} else {

				BookDto bookDto = new BookDto();
				bookDto.setIsbn(req.getParameter("isbn"));
				bookDto.setTitle(req.getParameter("title"));
				bookDto.setAuthor(req.getParameter("author"));
				bookDto.setPrice(Integer.parseInt(req.getParameter("price")));
				bookDto.setDesc(req.getParameter("desc"));
				try {
					if (bookService.verifyBook(bookDto)) {
						try {
							return bookService.registerBook(bookDto);
						} catch (Exception e) {
							e.printStackTrace();
							return "/error/error.jsp";
						}
					} else {
						req.setAttribute("msg", bookDto.getIsbn() + "은 이미 등록된 도서입니다.");
						return "/error/error.jsp";
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "/error/error.jsp";
				}
			}

		} else {
			req.setAttribute("msg", "로그인 후 이용해주세요");
			return "/error/error.jsp";
		}

	}

}
