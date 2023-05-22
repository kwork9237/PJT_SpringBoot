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
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("items/signin/login.html");
		return mav;
	}
	
	//Sign In (Regeister)
	//@RequestMapping(value = "/register")
	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/items/signin/register.html");
		return mav;
	}
	
	//Member Create
	@PostMapping("/create")
	public ModelAndView create(LoginVO log) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> map = new HashMap<>();
		map.put("mbMail", log.getMail());
		map.put("mbId", log.getId());
		
		Integer mbchk = mbService.chkMember(map);
		if(mbchk == 0) {
			//sha-512 encrypted password
			String[] enc_res = Encrypt.encrypt(log.getPw());
			
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
			
			mav.addObject("data", new Message("회원가입이 완료되었습니다.", "/"));
			mav.setViewName("Message");
		}
		
		else {
			mav.addObject("data", new Message("중복된 아이디 또는 이메일입니다.", "/register"));
			mav.setViewName("/static/Message.html");
			//System.out.println("Already exists id or email");
		}
		
		return mav;
	}
	
	//Get Post Items
	private ModelAndView getPosts() {
		ModelAndView mav = new ModelAndView();
		
		PostDomain posts = postService.getLastPost();
		
		return mav;
	}
}