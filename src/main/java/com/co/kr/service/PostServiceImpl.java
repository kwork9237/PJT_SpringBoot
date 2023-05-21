package com.co.kr.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.co.kr.domain.PostDomain;
import com.co.kr.mapper.PostMapper;

public class PostServiceImpl implements PostService {
	@Autowired
	private PostMapper postMapper;

	@Override
	public PostDomain getLastPost() {
		return postMapper.getLastPost();
	}
}
