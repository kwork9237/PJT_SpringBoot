package com.co.kr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.PostDomain;

@Mapper
public interface PostMapper {
	//READ
	public List<PostDomain> getLastPost();
	public PostDomain getSinglePost(Map<String, String> map);
	
	//CREATE
	public void createPost(PostDomain post);
	
	//UPDATE
	public void updatePost(PostDomain post);
	
	//DELETE
	public void deletePost(Map<String, String> map);
	
	//LIST
	public List<PostDomain> getPostList(Map<String, Integer> map);
	public Integer getPostCount();
}
