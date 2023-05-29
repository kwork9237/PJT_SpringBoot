package com.co.kr.service;

import java.util.List;
import java.util.Map;

import com.co.kr.domain.PostDomain;

public interface PostService {
	//READ
	public List<PostDomain> getLastPost();
	public PostDomain getSinglePost(Map<String, String> map);

	//CREATE
	public void createPost(PostDomain post);

	//UPDATE
	public void updatePost(PostDomain post);

	//DELETE
	public void deletePost(Map<String, String> map);
}
