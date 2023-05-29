package com.co.kr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class DevController {	
	//Error Page
	@PostMapping("/error")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView();
		System.out.println("WEB ERROR");
		mav.setViewName("redirect:/");

		return mav;
	}

	//DevMode Reset
	@RequestMapping(value = "/dev/reset")
	public ModelAndView devReset(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		System.out.println("DEV RESET (Http Attribute reset)");

		HttpSession hs = req.getSession();

		hs.setAttribute("isAdmin", 0);
		hs.setAttribute("isLoginned", 0);
		//hs.setAttribute("mbCode", 0);
		hs.setAttribute("devMode", 0);

		hs.removeAttribute("isAdmin");
		hs.removeAttribute("isLoginned");
		//hs.removeAttribute("mbCode");
		hs.removeAttribute("devMode");

		mav.setViewName("redirect:/");

		return mav;
	}

	//DevMode Enable
	@RequestMapping(value = "/dev/enable")
	public ModelAndView devEnable(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		System.out.println("DEV ENABLE (Http Attribute reset)");

		HttpSession hs = req.getSession();

		hs.setAttribute("isAdmin", 1);
		hs.setAttribute("devMode", 1);
		hs.setAttribute("isLoginned", 1);
		//hs.setAttribute("mbCode", 0);
		
		mav.setViewName("redirect:/");

		return mav;
	}
}
