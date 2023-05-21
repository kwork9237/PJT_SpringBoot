package com.co.kr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.PostDomain;

@Mapper
public interface PostMapper {
	public PostDomain getLastPost();
}
