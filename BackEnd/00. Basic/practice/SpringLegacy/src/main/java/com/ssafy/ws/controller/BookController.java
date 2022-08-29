package com.ssafy.ws.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.util.PageNavigation;
import com.ssafy.ws.dto.BookDto;
import com.ssafy.ws.dto.ImgDto;
import com.ssafy.ws.model.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private BookService bookService;

	@GetMapping("/list")
	public ModelAndView search(@RequestParam Map<String, String> map) {
		ModelAndView mav = new ModelAndView();
		String spp = map.get("spp");
		String pg = map.get("pg");
		map.put("spp", spp != null ? spp : "10");
		map.put("pg", pg != null ? pg : "1");

		try {
			List<BookDto> list = bookService.search(map);
			PageNavigation pageNavigation = bookService.makePageNavigation(map);
			mav.addObject("books", list);
			mav.addObject("navigation", pageNavigation);
			mav.addObject("key", map.get("key"));
			mav.addObject("word", map.get("word"));
			mav.setViewName("book/list");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "목록 출력 중 에러 발생");
			mav.setViewName("error/commonerr");
		}
		return mav;
	}

	@GetMapping("/regist")
	public String regist() {
		return "book/regist";
	}

	@PostMapping("/regist")
	public String insert(BookDto bookDto, Model model, @RequestParam("upimg") MultipartFile[] files) throws Exception {

		ImgDto imgDto = null;
		// 파일을 올린 경우에만 작동하는 코드
		if (!files[0].isEmpty()) {
			String realPath = servletContext.getRealPath("/upload");
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = realPath + File.separator + today;
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			imgDto = new ImgDto();
			String originalFileName = files[0].getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				imgDto.setIsbn(bookDto.getIsbn());
				imgDto.setSaveFolder(today);
				imgDto.setOriginFile(originalFileName);
				imgDto.setSaveFile(saveFileName);
				files[0].transferTo(new File(folder, saveFileName));
				bookDto.setImg(imgDto);
			}
		}
		bookService.insert(bookDto);
		// 파일을 올리지 않았으면 나머지 정보는 알아서 bookDto에 세팅된다.

		return "redirect:/book/regist_result?isbn=" + bookDto.getIsbn();
	}

	@GetMapping("/regist_result")
	public ModelAndView select(@RequestParam("isbn") String isbn) {
		ModelAndView mav = new ModelAndView();
		try {
			BookDto bookDto = bookService.select(isbn);
			mav.addObject("book", bookDto);
			mav.setViewName("book/regist_result");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "등록된 도서 조회에 실패했습니다.");
			mav.setViewName("error/commonerr");
		}
		return mav;
	}

	@GetMapping(value = "/download")
	public ModelAndView downloadFile(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
			@RequestParam("sfile") String sfile, HttpSession session) {
		Map<String, Object> fileInfo = new HashMap<String, Object>();
		fileInfo.put("sfolder", sfolder);
		fileInfo.put("ofile", ofile);
		fileInfo.put("sfile", sfile);

		return new ModelAndView("fileDownLoadView", "downloadFile", fileInfo);
	}

	@GetMapping(value = "/delete")
	public ModelAndView delete(@RequestParam("isbn") String isbn) {
		ModelAndView mav = new ModelAndView();
		try {
			bookService.delete(isbn);
			mav.setViewName("redirect:/book/list?pg=1&key=&word=");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "존재하지 않는 게시글입니다");
			mav.setViewName("error/commonerr");
		}
		return mav;
	}

	@GetMapping(value = "/update")
	public ModelAndView update(@RequestParam("isbn") String isbn) {
		ModelAndView mav = new ModelAndView();
		try {
			BookDto bookDto = bookService.select(isbn);
			mav.addObject("book", bookDto);
			mav.setViewName("book/modify");

		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "존재하지 않는 게시글입니다");
			mav.setViewName("error/commonerr");
		}
		return mav;

	}

	@PostMapping(value = "/update")
	public ModelAndView update(BookDto bookDto) {
		ModelAndView mav = new ModelAndView();
		try {
			bookService.update(bookDto);
			mav.setViewName("redirect:/book/list?pg=1&key=&word=");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "존재하지 않는 게시글입니다");
			mav.setViewName("error/commonerr");
		}
		return mav;
	}
}
