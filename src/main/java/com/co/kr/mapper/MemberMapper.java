package com.co.kr.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.LoginDomain;

@Mapper
public interface MemberMapper {
	public int chkMember(Map<String, String>map);
	public LoginDomain getMember(Map<String, String> map);
	public void createMember(LoginDomain log);
}
