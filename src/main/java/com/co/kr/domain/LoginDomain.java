package com.co.kr.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(builderMethodName="builder")
public class LoginDomain {
	private String mbCode;
	private String mbMail;
	private String mbId;
	private String mbPw;
	private String mbSalt;
	private String mbImage;
	private Integer isAdmin;
}
