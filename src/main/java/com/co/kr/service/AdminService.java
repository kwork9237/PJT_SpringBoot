package com.co.kr.service;

import java.util.List;
import java.util.Map;

import com.co.kr.domain.LoginDomain;

public interface AdminService {
	//READ
	public int getMemberCount();
	public List<LoginDomain> getMemberList(Map<String, Integer> map);
	public LoginDomain getSingleMember(Map<String, String> map);
	
	//UPDATE
	public void updateMemberInfo(LoginDomain log);
	public void updateMemberPassword(LoginDomain log);
}
