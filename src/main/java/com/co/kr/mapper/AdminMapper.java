package com.co.kr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.LoginDomain;

@Mapper
public interface AdminMapper {
	//READ
	public int getMemberCount();
	public List<LoginDomain> getMemberList(Map<String, Integer> map);
	public LoginDomain getSingleMember(Map<String, String> map);
	
	//UPDATE
	public void updateMemberInfo(LoginDomain log);
	public void updateMemberPassword(LoginDomain log);
}