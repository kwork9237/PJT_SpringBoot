package com.co.kr.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class PostVO {
	private String id;
	private String title;
	private String subtitle;
	private String content;
	private String tags;
	private String mimg;
	private String aimg;
}