package com.co.kr.service;

import java.util.Map;

import com.co.kr.domain.LoginDomain;

public interface MemberService {
	//READ
	public int chkMember(Map<String, String> map);
	public int chkCode(Map<String, String> map);
	public int chkMail(Map<String, String> map);
	public LoginDomain getMember(Map<String, String> map);
	public LoginDomain getCode(Map<String, String> map);
	
	//CREATE
	public void createMember(LoginDomain log);
	
	//UPDATE
	public void updateMember(LoginDomain log);
	
	//DELETE
	public void deleteMember(Map<String, String> map);
}