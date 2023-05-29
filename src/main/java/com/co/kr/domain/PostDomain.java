package com.co.kr.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(builderMethodName="builder")
public class PostDomain {
	private String postId;
	private String postAuthor;
	private String postAuthorId;
	private String postTitle;
	private String postContent;
	private String postSubtitle;
	private String postTags;
	private String postAuthorimg;
	private String postMimg;
	private String postDate;
}