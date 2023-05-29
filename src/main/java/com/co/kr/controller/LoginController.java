package com.co.kr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.LoginDomain;
import com.co.kr.service.MemberService;
import com.co.kr.utils.Encrypt;
import com.co.kr.utils.Message;
import com.co.kr.vo.LoginVO;

@Controller
@RequestMapping(value = "/")
public class LoginController {
	@Autowired
	private MemberService mbService;
	
	//Logout
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession hs = req.getSession();
		hs.removeAttribute("mbCode");
		hs.removeAttribute("isLoginned");
		hs.removeAttribute("isAdmin");
		
		//쿠키 제거
		Cookie[] cookies = req.getCookies();
				
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
				res.addCookie(cookies[i]);
			}				
		}
		
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	//Login
	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		HttpSession hs = req.getSession();
		
		//로그인시 강제 리다이렉트
		try {
			if((Integer)hs.getAttribute("isLoginned") == 1) {
				//System.out.println("logined value is null");
				mav.setViewName("redirect:/main");
			}
			else
				mav.setViewName("items/signin/login.html");
		}
		catch(NullPointerException e) {
			mav.setViewName("items/signin/login.html");
		}
		
		return mav;
	}
	
	//Sign in
	@PostMapping("/signin")
	public ModelAndView signin(LoginVO log, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		LoginDomain mbInfo;
		
		Map<String, String> map = new HashMap<>();
		map.put("mbId", log.getId());
		map.put("mbMail", log.getMail());
		
		try {
			mbInfo = mbService.getMember(map);
			
			Integer loginChk = Encrypt.pwCheck(log.getPw(), mbInfo.getMbSalt(), mbInfo.getMbPw());
			if(loginChk == 1) {
				System.out.println(log.getId() + " : was loginned");
				mav.addObject("data", new Message("로그인에 성공하셨습니다.", "/"));
				mav.setViewName("/static/Message");
				
				//Session 처리
				HttpSession hs = req.getSession();
				hs.setAttribute("mbCode", mbInfo.getMbCode());
				hs.setAttribute("isLoginned", 1);
				hs.setAttribute("isAdmin", mbInfo.getIsAdmin());
			}
			
			else {
				mav.addObject("data", new Message("틀린 ID 또는 PW입니다.", "/login"));
				mav.setViewName("/static/Message");
			}
		}
		catch(NullPointerException err) {
			System.out.println("ERROR : NullPointerException => " + err);
			mav.addObject("data", new Message("틀린 ID 또는 PW입니다.", "/login"));
			mav.setViewName("/static/Message");
		}
		
		return mav;
	}
	
	//Register
	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/items/signin/register.html");
		return mav;
	}

	//Create
	@PostMapping("/create")
	public ModelAndView create(LoginVO log) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> map = new HashMap<>();
		map.put("mbMail", log.getMail());
		map.put("mbId", log.getId());
		
		Integer mbchk = mbService.chkMember(map);
		if(mbchk == 0) {
			//sha-512 encrypted password
			String[] enc_res = Encrypt.pwEncrypt(log.getPw());
			String image = "https://http.cat/500";
			
			//loginDomain Build
			LoginDomain logDomain = LoginDomain.builder()
					.mbName(log.getName())
					.mbMail(log.getMail())
					.mbId(log.getId())
					.mbPw(enc_res[0])
					.mbSalt(enc_res[1])
					.mbImage(image) //DUMMY Image
					.build();
			
			//MbCreate
			mbService.createMember(logDomain);
			
			mav.addObject("data", new Message("회원가입이 완료되었습니다.", "/login"));
			mav.setViewName("/static/Message");
		}
		
		else {
			mav.addObject("data", new Message("중복된 아이디 또는 이메일입니다.", "/register"));
			mav.setViewName("/static/Message");
		}
		
		return mav;
	}
}
