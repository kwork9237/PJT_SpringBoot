package com.co.kr.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(builderMethodName="builder")
public class PostDomain {
	private String postAuthor;
	private String postType;
	private String postDate;
	private String postImg;
	private String postDetail;
}
