package com.co.kr.service;

import java.util.List;
import java.util.Map;

import com.co.kr.domain.PostDomain;

public interface PostService {
	public List<PostDomain> getLastPost();
	public PostDomain getSinglePost(Map<String, String> map);
}
