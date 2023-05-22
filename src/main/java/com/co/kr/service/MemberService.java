package com.co.kr.service;

import java.util.Map;

import com.co.kr.domain.LoginDomain;

public interface MemberService {
	public int chkMember(Map<String, String>map);
	public LoginDomain getMember(Map<String, String> map);
	public void createMember(LoginDomain log);
}