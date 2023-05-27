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

	//READ
	@Override
	public int chkMember(Map<String, String> map) {
		return mbMapper.chkMember(map);
	}
	
	@Override
	public LoginDomain getMember(Map<String, String> map) {
		return mbMapper.getMember(map);
	}

	@Override
	public int chkCode(Map<String, String> map) {
		return mbMapper.chkMember(map);
	}

	@Override
	public LoginDomain getCode(Map<String, String> map) {
		return mbMapper.getCode(map);
	}
	
	//CREATE
	@Override
	public void createMember(LoginDomain log) {
		mbMapper.createMember(log);
	}

	//UPDATE
	@Override
	public void updateMember(LoginDomain log) {
		mbMapper.updateMember(log);
	}

	//DELETE
	@Override
	public void deleteMember(Map<String, String> map) {
		mbMapper.deleteMember(map);
	}
}