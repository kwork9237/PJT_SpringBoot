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

	//READ
	@Override
	public List<PostDomain> getLastPost() {
		return postMapper.getLastPost();
	}

	@Override
	public PostDomain getSinglePost(Map<String, String> map) {
		return postMapper.getSinglePost(map);
	}

	//CREATE
	@Override
	public void createPost(PostDomain post) {
		postMapper.createPost(post);
	}

	//UPDATE
	@Override
	public void updatePost(PostDomain post) {
		postMapper.updatePost(post);
	}

	//DELETE
	@Override
	public void deletePost(Map<String, String> map) {
		postMapper.deletePost(map);
	}

	@Override
	public List<PostDomain> getPostList(Map<String, Integer> map) {
		return postMapper.getPostList(map);
	}

	@Override
	public Integer getPostCount() {
		return postMapper.getPostCount();
	}
}
