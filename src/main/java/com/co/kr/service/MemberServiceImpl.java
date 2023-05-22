package com.co.kr.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.kr.domain.LoginDomain;
import com.co.kr.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberMapper mbMapper;

	@Override
	public int chkMember(Map<String, String> map) {
		return mbMapper.chkMember(map);
	}
	
	@Override
	public LoginDomain getMember(Map<String, String> map) {
		return mbMapper.getMember(map);
	}

	@Override
	public void createMember(LoginDomain log) {
		mbMapper.createMember(log);
	}
}