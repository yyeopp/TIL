package com.ssafy.happyhouse.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;

	@Override
	@Transactional
	public void registerMember(MemberDto memberDto) throws Exception {
		memberMapper.registMember(memberDto);
	}

	@Override
	public MemberDto getMember(Map<String, String> map) throws Exception {

		return memberMapper.getMember(map);
	}

	@Override
	public String getPassword(MemberDto memberDto) throws Exception {
		return memberMapper.getPassword(memberDto);
	}

	@Override
	@Transactional
	public void updateMember(MemberDto memberDto) throws Exception {
		memberMapper.updateMember(memberDto);
	}

	@Override
	public void deleteMember(String id) throws Exception {
		
	}

	@Override
	public int idCheck(String id) throws Exception {
		return memberMapper.idCheck(id);
	}

	@Override
	public MemberDto getInfo(String id) throws Exception {
		return null;
	}

}
