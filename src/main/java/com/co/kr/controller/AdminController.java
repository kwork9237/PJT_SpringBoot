package com.co.kr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.LoginDomain;
import com.co.kr.service.AdminService;
import com.co.kr.service.MemberService;
import com.co.kr.utils.Encrypt;
import com.co.kr.utils.Message;
import com.co.kr.utils.Pagination;
import com.co.kr.vo.LoginVO;

@Controller
@RequestMapping(value = "/")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MemberService mbService;
	
	//Manage page
	@GetMapping("/manage")
	public ModelAndView manage(@RequestParam("page") String page, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> pegMap = new HashMap<>();
		pegMap = Pagination.pagination(adminService.getMemberCount(), req);
		
		Map<String, Integer> memberMap = new HashMap<>();
		memberMap.put("start", (Integer) pegMap.get("offset"));
		List<LoginDomain> members = adminService.getMemberList(memberMap);
		
		mav.addAllObjects(pegMap);
		mav.addObject("items", members);
		mav.addObject("itemsNotEmpty", true);
		
		mav.setViewName("items/admin/main.html");
		return mav; 
	}
	
	//Update member
	@GetMapping("/manage/change/{mbCode}")
	public ModelAndView userManage(@PathVariable("mbCode") String mbCode) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> memberMap = new HashMap<>();
		memberMap.put("mbCode", mbCode);
		LoginDomain mbData = adminService.getSingleMember(memberMap);
		
		//Add MemberData
		mav.addObject("mbCode", mbData.getMbCode());
		mav.addObject("mbName", mbData.getMbName());
		mav.addObject("mbMail", mbData.getMbMail());
		mav.addObject("mbId", mbData.getMbId());
		mav.addObject("mbImage", mbData.getMbImage());
		mav.addObject("isAdmin", mbData.getIsAdmin());
		
		mav.setViewName("/items/admin/manage.html");
		return mav;
	}
	
	//Commit Changes
	@PostMapping("/manage/commit/{mbCode}")
	public ModelAndView commitChanges(LoginVO log, @PathVariable("mbCode") String mbCode) {
		ModelAndView mav = new ModelAndView();

		if(log.getPw() == "") {
			LoginDomain newMbInfo = LoginDomain.builder()
					.mbCode(log.getCode())
					.mbName(log.getName())
					.mbMail(log.getMail())
					.mbId(log.getId())
					.mbImage("https://http.cat/500") //DUMMY Image
					.isAdmin(Integer.parseInt(log.getIsadmin()))
					.build();
			
			adminService.updateMemberInfo(newMbInfo);
			
			mav.addObject("data", new Message("회원정보가 수정되었습니다.", "/manage?page=1"));
			mav.setViewName("/static/Message");
		}
		
		else if(log.getPw() != "") {
			String[] newPw = Encrypt.pwEncrypt(log.getPw());
			
			LoginDomain newMbInfo = LoginDomain.builder()
					.mbCode(log.getCode())
					.mbName(log.getName())
					.mbMail(log.getMail())
					.mbId(log.getId())
					.mbPw(newPw[0])
					.mbSalt(newPw[1])
					.mbImage("https://http.cat/500") //DUMMY Image
					.isAdmin(Integer.parseInt(log.getIsadmin()))
					.build();
			
			adminService.updateMemberInfo(newMbInfo);
			adminService.updateMemberPassword(newMbInfo);
			
			mav.addObject("data", new Message("회원정보와 비밀번호가 수정되었습니다.", "/manage?page=1"));
			mav.setViewName("/static/Message");
			
		}
		
		else {
			mav.addObject("data", new Message("ERROR", "/manage?page=1"));
			mav.setViewName("/static/Message");
		}
		
		return mav;
	}
	
	//Member Delete
	
	//Delete member
	//DELETE Member
	@GetMapping("/manage/delete/{mbCode}")
	public ModelAndView userDelete(@PathVariable("mbCode") String mbCode) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> memberMap = new HashMap<>();
		memberMap.put("mbCode", mbCode);
	
		try {
			LoginDomain oldMbData = adminService.getSingleMember(memberMap);
			
			//Delete Member
			mbService.deleteMember(memberMap);
	
			//Print Delete Member
			//System.out.println(oldMbData.getMbId());
			
			//Redirect
			mav.addObject("data", new Message("회원 [ " + oldMbData.getMbId() + " ] 가 삭제되었습니다.", "/manage?page=1"));
			mav.setViewName("/static/Message");
		}
		catch (NullPointerException err) {
			mav.addObject("data", new Message("해당 회원은 존재하지 않습니다.", "/manage?page=1"));
			mav.setViewName("/static/Message");
		}
		return mav;
	}
}