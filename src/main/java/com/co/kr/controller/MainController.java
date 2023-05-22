package com.co.kr.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.LoginDomain;
import com.co.kr.domain.PostDomain;
import com.co.kr.service.MemberService;
import com.co.kr.service.PostService;
import com.co.kr.vo.LoginVO;

@Controller
@RequestMapping(value = "/")
public class MainController {
	@Autowired
	private MemberService mbService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/main")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/");
		return mav;
	}
	
	@GetMapping("/about")
	public ModelAndView about() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("about.html");
		return mav;
	}
	
	@GetMapping("/contact")
	public ModelAndView contact() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("contact.html");
		return mav;
	}
	
	@GetMapping("/pricing")
	public ModelAndView pricing() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("pricing.html");
		return mav;
	}
	
	@GetMapping("/faq")
	public ModelAndView faq() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("faq.html");
		return mav;
	}
	
	@GetMapping("/bloghome")
	public ModelAndView bloghome() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("blog-home.html");
		return mav;
	}
	
	@GetMapping("/blogpost")
	public ModelAndView blogpost() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("blog-post.html");
		return mav;
	}
	
	@GetMapping("/portfolioview")
	public ModelAndView portfolioview() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("portfolio-overview.html");
		return mav;
	}
	
	@GetMapping("/portfolioitem")
	public ModelAndView portfolioitem() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("portfolio-item.html");
		return mav;
	}
	
	//Sign In
	@PostMapping("/signin")
	@RequestMapping("/signin")
	public ModelAndView signin(LoginVO loginVO) {
		ModelAndView mav = new ModelAndView();
		
		/*
		Map <String, String> map = new HashMap<>();
		map.put("mbMail", loginVO.getEmail());
		//System.out.println("chkmember " + mbService.chkMember(map));
		//System.out.println("mail addr " + loginVO.getEmail());
		if(mbService.chkMember(map) == 0) {
			//System.out.println("Signed!");
			LoginDomain ld = LoginDomain.builder()
					.mbMail(loginVO.getEmail())
					.mbId(loginVO.getId())
					.mbPw(loginVO.getPw())
					.build();
		}
		*/
		
		mav.setViewName("signin.html");
		return mav;
	}
	
	//Get Post Items
	private ModelAndView getPosts() {
		ModelAndView mav = new ModelAndView();
		
		PostDomain posts = postService.getLastPost();
		
		return mav;
	}
}
