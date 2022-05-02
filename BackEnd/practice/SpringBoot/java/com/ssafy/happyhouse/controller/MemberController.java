package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@GetMapping("/login")
	public String mvlogin() {
		return "member/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam Map<String, String> map, Model model, HttpSession session) {

		try {
			MemberDto memberDto = memberService.getMember(map);
			if (memberDto != null) {
				session.setAttribute("memberInfo", memberDto);
				return "redirect:/";
			} else {
				model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요");
				return "member/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "로그인 처리 중 문제가 발생했습니다.");
			return "index";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/signup")
	public String signup() {
		return "member/signup";
	}

	@PostMapping("/regist")
	public String regist(MemberDto memberDto, Model model) {
		try {
			memberService.registerMember(memberDto);
			model.addAttribute("msg", "회원 가입을 완료했습니다.");
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원 가입 중 문제가 발생했습니다.");
			return "index";
		}
	}

	@GetMapping("/idcheck")
	@ResponseBody
	public String idcheck(@RequestParam String id) {
		int cnt = 1;
		try {
			cnt = memberService.idCheck(id);
			JSONObject json = new JSONObject();
			json.put("idcount", cnt);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("idcount", 1);
		return json.toString();
	}

	@GetMapping("/findpwd")
	public String findPwd() {
		return "member/findpwd";
	}

	@PostMapping("/findpwd")
	public String getPassword(MemberDto memberDto, Model model) {
		try {
			String pw = memberService.getPassword(memberDto);
			model.addAttribute("msg", "비밀번호는 " + pw + "입니다.");
			return "member/login";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "비밀번호 찾기 중 문제가 발생했습니다.");
			return "member/login";

		}
	}

	// mypage 버튼을 클릭하면 session에서 id정보를 얻어와서 유저정보를 get 해오는 방식으로 수정
	// jsp 파일도 일부 수정이 필요함
	@GetMapping("/mypage")
	public String myPage(HttpSession session) {
		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
		System.out.println(memberDto.toString());
		return "member/mypage";
	}

	@PostMapping("/update")
	public String updateMember(MemberDto memberDto, Model model, HttpSession session) {
		try {
			System.out.println(memberDto.toString());
			memberService.updateMember(memberDto);
			return "redirect:/member/mypage";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원 정보 수정 중 문제가 발생했습니다.");
			return "member/mypage";
		}
	}

}
