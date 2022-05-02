package com.ssafy.happyhouse.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.service.NoticeService;
import com.ssafy.happyhouse.util.PageNavigation;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	NoticeService noticeService;

	@GetMapping("/list")
	public String getNoticeList(@RequestParam Map<String, String> map, Model model) {

		String pgno = map.get("pg") != null ? map.get("pg") : "1";
		map.put("pg", pgno);
		String key = map.get("key") != null ? map.get("key").trim() : "";
		map.put("key", key);
		String word = map.get("word") != null ? map.get("word").trim() : "";
		map.put("word", word);

		try {
			List<NoticeDto> list = noticeService.getNoticeList(map);
			PageNavigation navigation = noticeService.makePageNavigation(map);
			model.addAttribute("noticeList", list);
			model.addAttribute("navi", navigation);
			model.addAttribute("key", key);
			model.addAttribute("word", word);
			return "notice/notice";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "공지를 조회하는 데 오류가 발생했습니다.");
			return "index";
		}

	}

	@GetMapping("/view/{no}")
	public String getNotice(@PathVariable int no, Model model) {
		try {
			NoticeDto noticeDto = noticeService.getNotice(no);
			model.addAttribute("notice", noticeDto);
			return "notice/noticeview";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "공지를 조회하는 데 오류가 발생했습니다.");
			return "notice/notice";
		}
	}

	@GetMapping("/write")
	public String write() {
		return "notice/noticewrite";
	}

	@PostMapping("/write")
	public String writeNotice(NoticeDto noticeDto, Model model) {

		try {
			noticeService.writeNotice(noticeDto);
			return "redirect:/notice/list";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "공지를 등록하는 데 오류가 발생했습니다.");
			return "notice/notice";
		}

	}

	@GetMapping("/update/{no}")
	public String update(@PathVariable int no, Model model) {
		NoticeDto noticeDto;
		try {
			noticeDto = noticeService.getNotice(no);
			model.addAttribute("notice", noticeDto);
			return "notice/noticeupdate";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "공지를 조회하는 데 오류가 발생했습니다.");
			return "notice/notice";
		}
	}
	
	@PostMapping("/update")
	public String updateNotice(NoticeDto noticeDto, Model model) {
		
		try {
			noticeService.updateNotice(noticeDto);
			return "redirect:/notice/view/" + noticeDto.getNo();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "공지를 수정하는 데 오류가 발생했습니다.");
			return "notice/notice";
		}
		
	}

	@GetMapping("/delete/{no}")
	public String delete(@PathVariable int no, Model model) {
		try {
			noticeService.deleteNotice(no);
			return "redirect:/notice/list";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "공지를 삭제하는 데 오류가 발생했습니다.");
			return "notice/notice";
		}
	}

}
