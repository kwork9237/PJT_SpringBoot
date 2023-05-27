package com.co.kr.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.LoginDomain;
import com.co.kr.domain.PostDomain;
import com.co.kr.utils.Encrypt;
import com.co.kr.utils.Message;
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
	
	//Profile
	@GetMapping("/profile")
	public ModelAndView profile(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession hs = req.getSession();
		String code = (String) hs.getAttribute("mbCode");
		
		Map<String, String> map = new HashMap<>();
		map.put("mbCode", code);
		
		LoginDomain mbInfo = mbService.getCode(map);
		
		mav.addObject("mbCode", mbInfo.getMbCode());
		mav.addObject("mbMail", mbInfo.getMbMail());
		mav.addObject("mbId", mbInfo.getMbId());
		mav.addObject("mbPw", mbInfo.getMbPw());
		mav.setViewName("items/profile/profile.html");
		return mav;
	}
	
	@PostMapping("/profile/update")
	public ModelAndView profileUpdate(LoginVO log, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> map = new HashMap<>();
		map.put("mbCode", log.getCode());
		
		LoginDomain mbInfo = mbService.getCode(map);
		LoginDomain newMbInfo;
		
		if(mbService.chkMember(map) != 1) {
			mav.addObject("data", new Message("로그인에 성공하셨습니다.", "/"));
			mav.setViewName("/static/Message");
		}
		
		else {
			if(mbInfo.getMbPw().equals(log.getPw())) {
				newMbInfo = LoginDomain.builder()
						.mbMail(log.getMail())
						.mbId(log.getId())
						.build();
			}
			else {
				String[] newPw = Encrypt.pwEncrypt(log.getPw());
				
				newMbInfo = LoginDomain.builder()
						.mbMail(log.getMail())
						.mbId(log.getId())
						.mbPw(newPw[0])
						.mbSalt(newPw[1])
						.build();
			}
			
			mbService.updateMember(newMbInfo);
			mav.setViewName("redirect:/profile");
		}
		/*
		System.out.println(log.getCode());
		System.out.println(log.getId());
		System.out.println(log.getMail());
		System.out.println(log.getPw());
		*/
		
		return mav;
	}
	
	//Logout
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession hs = req.getSession();
		hs.removeAttribute("isLoginned");
		
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
	
	//Regeister
	
	//Register
	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/items/signin/register.html");
		return mav;
	}
	
	//Member Create
	
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
			
			//TEST
			//String[] enc_res = Encrypt.encrypt("aaaaa");
			
			//loginDomain Build
			LoginDomain logDomain = LoginDomain.builder()
					.mbMail(log.getMail())
					.mbId(log.getId())
					.mbPw(enc_res[0])
					.mbSalt(enc_res[1])
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