package com.ssafy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssafy.ws.dto.UserDto;

@SuppressWarnings("deprecation")
public class LoginConfirmInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { // Controller 가기 전에 핸들한다는 뜻
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userInfo");
		if (userDto == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false; // return false해서 컨트롤러로의 이동을 막아버림
		}
		return true;
	}
}
