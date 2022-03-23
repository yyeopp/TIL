package com.ssafy.backend.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.backend.dto.User;

//이 서블릿이 호출되기 위해서는 url 상에 http://server_ip:port/context_name/main 이 필요하다.
@WebServlet("/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
    /**
     * get 방식의 요청에 대해 응답하는 메서드이다.
     * front controller pattern을 적용하기 위해 내부적으로 process를 호출한다.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}
	
	
    /**
     * post 방식의 요청에 대해 응답하는 메서드이다.
     * front controller pattern을 적용하기 위해 내부적으로 process를 호출한다.
     */
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        // post 요청 시 한글 파라미터의 처리를 위해 encoding을 처리한다.
        request.setCharacterEncoding("utf-8");
        process(request, response);
    }
	
    /**
     * request 객체에서 action 파라미터를 추출해서 실제 비지니스 로직 메서드(ex: doRegist) 
     * 호출해준다.
     */
	private void process(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("sign");
		switch (action) {
		case "regist":
			doRegist(request, response);
			break;
		}
	}
	
    /**
     * 사용자 정보를 등록하기 위해 파라미터가 잘 전달되는지 확인하고 전달 결과를 화면에 출력한다.
     * 이를 위해 request에서 전달 받은 내용을 추출해서 User 객체를 생성한 후 response로 출력한다.
     * 특히 response 시 content의 형식에 주의한다.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	private void doRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request 객체에서 전달된 parameter를 추출한다.
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		// 문자열로 전달된 age는 숫자로 변환
		int age = Integer.parseInt(request.getParameter("age"));
		
		// 전달받은 parameter를 이용해서 User 객체를 생성한다. 
		User user = new User(id, password, name, email, age);
		
		// 화면에 출력할 데이터를 구성한다. 
		StringBuilder output = new StringBuilder();
		
		output.append("<html><body>").append("<h1>사용자 정보</h1>").append(user.toString()).append("</body></html>");
		
		// response 객체를 통해서 생성한 html코드를 출력한다.
		// 응답이 어떤 타입인지 설정
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(output.toString());
		
	}
}
