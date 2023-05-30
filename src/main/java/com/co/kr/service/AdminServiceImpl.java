package com.co.kr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.kr.domain.LoginDomain;
import com.co.kr.mapper.AdminMapper;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public int getMemberCount() {
		return adminMapper.getMemberCount();
	}

	@Override
	public List<LoginDomain> getMemberList(Map<String, Integer> map) {
		return adminMapper.getMemberList(map);
	}

	@Override
	public LoginDomain getSingleMember(Map<String, String> map) {
		return adminMapper.getSingleMember(map);
	}

	@Override
	public void updateMemberInfo(LoginDomain log) {
		adminMapper.updateMemberInfo(log);		
	}

	@Override
	public void updateMemberPassword(LoginDomain log) {
		adminMapper.updateMemberPassword(log);
	}
}