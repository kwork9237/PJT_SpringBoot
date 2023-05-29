package com.co.kr.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class Pagination {
	public static Map<String, Object> pagination(int total, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();

		int page = Integer.parseInt(req.getParameter("page"));

		int rowNUM = page;
		if(rowNUM < 0) rowNUM = 1;
		
		int pageNum;
		if (total % 10 == 0) pageNum = total / 10;
		else pageNum = (total / 10) + 1;
		
		if(rowNUM > pageNum) rowNUM = pageNum;
		
		int temp = (rowNUM - 1) % 10;
		int startpage = rowNUM - temp;
		int endpage = startpage + (10 - 1);
		
		if (endpage > pageNum) endpage = pageNum;
		
		int offset = (rowNUM - 1) * 10;
		
		map.put("rowNUM", rowNUM);
		map.put("pageNum", pageNum);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("offset", offset);

		return map;
	}
}
