package com.co.kr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.PostDomain;

@Mapper
public interface PostMapper {
	public List<PostDomain> getLastPost();
	public PostDomain getSinglePost(Map<String, String> map);
}
