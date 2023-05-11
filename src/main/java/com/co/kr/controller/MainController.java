package com.co.kr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/")
public class MainController {
	@GetMapping("/main")
	public ModelAndView index(RedirectAttributes redir) {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("redirect:/");
		return mav;
	}
	
	@GetMapping("/about")
	public String about() {
		return "about.html";
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "contect.html";
	}
	
	@GetMapping("/pricing")
	public String pricing() {
		return "pricing.html";
	}
	
	@GetMapping("/faq")
	public String faq() {
		return "faq.html";
	}
	
	@GetMapping("/bloghome")
	public String bloghome() {
		return "blog-home.html";
	}
	
	@GetMapping("/blogpost")
	public String blogpost() {
		return "blog-post.html";
	}
}
