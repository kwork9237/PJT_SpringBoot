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
public class ProfileController {
	@Autowired
	private MemberService mbService;
	
	//Profile
	@GetMapping("/profile")
	public ModelAndView profile(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession hs = req.getSession();
		String code = (String) hs.getAttribute("mbCode");
		
		Map<String, String> map = new HashMap<>();
		map.put("mbCode", code);
		
		LoginDomain mbInfo = mbService.getCode(map);
		
		mav.addObject("mbName", mbInfo.getMbName());
		mav.addObject("mbCode", mbInfo.getMbCode());
		mav.addObject("mbMail", mbInfo.getMbMail());
		mav.addObject("mbId", mbInfo.getMbId());
		mav.addObject("mbImage", mbInfo.getMbImage()); //DUMMY Image
		mav.setViewName("items/profile/profile.html");
		return mav;
	}
	
	//Profile Update
	@PostMapping("/profile/update")
	public ModelAndView profileUpdate(LoginVO log, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> map = new HashMap<>();
		map.put("mbCode", log.getCode());

		LoginDomain oldMbInfo = mbService.getCode(map);
		
		if(oldMbInfo.getMbMail().equals(log.getMail())) {
			String[] newPw = Encrypt.pwEncrypt(log.getPw());
			LoginDomain newMbInfo = LoginDomain.builder()
					.mbCode(log.getCode())
					.mbName(log.getName())
					.mbMail(log.getMail())
					.mbId(log.getId())
					.mbPw(newPw[0])
					.mbSalt(newPw[1])
					.mbImage("https://http.cat/500") //DUMMY Image
					.build();
			
			mbService.updateMember(newMbInfo);
			mav.addObject("data", new Message("비밀번호가 변경되었습니다.", "/main"));
			mav.setViewName("/static/Message");
		}
		else {
			String[] newPw = Encrypt.pwEncrypt(log.getPw());
			LoginDomain newMbInfo = LoginDomain.builder()
					.mbCode(log.getCode())
					.mbName(log.getName())
					.mbMail(log.getMail())
					.mbId(log.getId())
					.mbPw(newPw[0])
					.mbSalt(newPw[1])
					.mbImage("https://http.cat/500") //DUMMY Image
					.build();
	
			mbService.updateMember(newMbInfo);
			mav.addObject("data", new Message("이메일과 비밀번호가 변경되었습니다.", "/main"));
			mav.setViewName("/static/Message");
		}
		return mav;
	}

	//Profile Delte
	@GetMapping("/profile/delete")
	public ModelAndView profileDelte(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView();
		HttpSession hs = req.getSession();
		
		String mbCode = (String) hs.getAttribute("mbCode");
		Map<String, String> map = new HashMap<>();
		map.put("mbCode", mbCode);
		
		mbService.deleteMember(map);
		
		//clear data		
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
		
		mav.addObject("data", new Message("회원탈퇴가 완료되었습니다.", "/login"));
		mav.setViewName("/static/Message");
		
		return mav;
	}
	
}
