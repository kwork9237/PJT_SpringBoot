package com.co.kr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.kr.domain.PostDomain;
import com.co.kr.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostMapper postMapper;

	@Override
	public List<PostDomain> getLastPost() {
		return postMapper.getLastPost();
	}

	@Override
	public PostDomain getSinglePost(Map<String, String> map) {
		return postMapper.getSinglePost(map);
	}
}
