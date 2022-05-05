package com.ssafy.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionControllerAdvice {
	private Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, Model model) {

		logger.error("Exception 발생 : {}", ex.getMessage());
		model.addAttribute("msg", "파라미터가 잘 전달되었는지 확인하세요.");
		return "error/commonerr";
	}

	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex, Model model) {
		logger.error("404 발생 : {}", "404 page not found");
		model.addAttribute("msg", "해당 페이지를 찾을 수 없습니다.");
		return "error/404";
	}
}
