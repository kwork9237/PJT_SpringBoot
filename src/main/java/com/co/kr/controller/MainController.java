package com.co.kr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.utils.Message;

@Controller
@RequestMapping(value = "/")
public class MainController {	
	//Main
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
	
	//About
	@GetMapping("/about")
	public ModelAndView about() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/etc/about.html");
		return mav;
	}
	
	//Contact
	@GetMapping("/contact")
	public ModelAndView contact() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/etc/contact.html");
		return mav;
	}
	
	//FAQ
	@GetMapping("/faq")
	public ModelAndView faq() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/etc/faq.html");
		return mav;
	}
	
	///////////////////////////////////////////////////////////////////
	//Not Enable Pages
	//Price (Not Enable)
	@GetMapping("/pricing")
	public ModelAndView pricing() {
		ModelAndView mav = new ModelAndView();			
		mav.setViewName("items/etc/pricing.html");
		return mav;
	}
	
	//Use Only Dev Mode
	@GetMapping("/portfolio/view")
	public ModelAndView portfolioview() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/portfolio/overview.html");
		return mav;
	}
	
	@GetMapping("/portfolio/item")
	public ModelAndView portfolioitem() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/portfolio/items.html");
		return mav;
	}
}