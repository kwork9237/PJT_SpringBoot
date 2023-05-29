package com.co.kr.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
	public int getMemberCount();
}
