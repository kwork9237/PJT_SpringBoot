package com.co.kr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.PostDomain;
import com.co.kr.utils.Message;
import com.co.kr.service.MemberService;
import com.co.kr.service.PostService;

@Controller
@RequestMapping(value = "/")
public class MainController {
	@Autowired
	private MemberService mbService;
	
	@Autowired
	private PostService postService;
	
	//main process
	@GetMapping("/")
	public ModelAndView main(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		//Session 확인
		HttpSession hs = req.getSession();
		Integer chk = 0;
		
		try {
			chk = (Integer) hs.getAttribute("isLoginned");
			//System.out.println("chk : " + chk);
			
			if(chk != null)
				mav.setViewName("redirect:/main");
			else {
				mav.addObject("data", new Message("로그인 확인이 불가능합니다!", "/login"));
				mav.setViewName("/static/Message");
			}
				
		}
		catch (NullPointerException err) {
			System.out.println("logined value not found => " +  err);
			
			//make session value
			hs.setAttribute("isLoginned", 0);
			mav.setViewName("items/signin/login.html");
		}

		return mav;
	}
	
	@GetMapping("/main")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("index.html");
		return mav;
	}
	
	@GetMapping("/about")
	public ModelAndView about() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/etc/about.html");
		return mav;
	}
	
	@GetMapping("/contact")
	public ModelAndView contact() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/etc/contact.html");
		return mav;
	}
	
	@GetMapping("/pricing")
	public ModelAndView pricing() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/etc/pricing.html");
		return mav;
	}
	
	@GetMapping("/faq")
	public ModelAndView faq() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/etc/faq.html");
		return mav;
	}
	
	@GetMapping("/bloghome")
	public ModelAndView bloghome() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/blog/home.html");
		return mav;
	}
	
	@GetMapping("/blogpost")
	public ModelAndView blogpost() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/blog/post.html");
		return mav;
	}
	
	@GetMapping("/portfolioview")
	public ModelAndView portfolioview() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/portfolio/overview.html");
		return mav;
	}
	
	@GetMapping("/portfolioitem")
	public ModelAndView portfolioitem() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/portfolio/items.html");
		return mav;
	}
	


	//Get Post Items
	private ModelAndView getPosts() {
		ModelAndView mav = new ModelAndView();
		
		PostDomain posts = postService.getLastPost();
		
		return mav;
	}
	
	
	//SSL Check (No Use)
	/*
	@RequestMapping(value = "/.well-known/acme-challenge/{name}")
	public ModelAndView sslcheck(@PathVariable("name") String name) {
		ModelAndView mav = new ModelAndView();
		System.out.println("name chk : " + name);
		mav.setViewName("redirect:/");

		return mav;
	}
	*/
	
	//Error시 강제 리턴

	//Error
	@PostMapping("/error")
	public ModelAndView errorOverrie() {
		ModelAndView mav = new ModelAndView();
		System.out.println("WEB ERROR");
		mav.setViewName("redirect:/");

		return mav;
	}
	
	//--------------------------------------------------
	//DEV
	@RequestMapping(value = "/dev")
	public ModelAndView dev(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		System.out.println("DEV Enable (Http Attribute reset)");
		
		HttpSession hs = req.getSession();
		
		hs.setAttribute("isLoginned", 0);
		hs.removeAttribute("isLoginned");
		
		
		mav.setViewName("redirect:/");

		return mav;
	}
}