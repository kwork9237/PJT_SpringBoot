package com.co.kr.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.LoginDomain;
import com.co.kr.domain.PostDomain;
import com.co.kr.service.MemberService;
import com.co.kr.service.PostService;
import com.co.kr.utils.Message;
import com.co.kr.utils.Pagination;
import com.co.kr.vo.PostVO;

@Controller
@RequestMapping(value = "/")
public class BlogController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private MemberService mbService;
	
	//Show home
	@GetMapping("/blog/home")
	public ModelAndView blogHome() {
		ModelAndView mav = new ModelAndView();
		
		List<PostDomain> posts = postService.getLastPost();
		
		if(posts != null) {
			mav.addObject("items", posts);
			mav.addObject("itemsNotEmpty", true);
		}
		
		else {
			mav.addObject("itemsNotEmpty", false);
		}
		
		mav.setViewName("items/blog/home.html");
		return mav;
	}
	
	//Show post(postId)
	@GetMapping("/blog/post/{postId}")
	public ModelAndView blogPost(@PathVariable("postId") String postId) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> map = new HashMap<>();
		map.put("postId", postId);
		
		PostDomain post = postService.getSinglePost(map);
		
		if(post != null) {
			mav.addObject("item", post);
		}
		
		else {
			mav.addObject("item", returnErrPost());
		}
		
		mav.setViewName("items/blog/post.html");
		return mav;
	}
	
	//Show List(post)
	//
	@GetMapping("/blog/postList")
	public ModelAndView postList(@RequestParam("page") String page, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> pegmap;
		pegmap = Pagination.pagination(postService.getPostCount(), req);
		
		Map<String, Integer> postMap =new HashMap<>();
		postMap.put("start", (Integer) pegmap.get("offset"));
		List <PostDomain> posts = postService.getPostList(postMap);
		
		if(posts != null) {
			mav.addAllObjects(pegmap);
			mav.addObject("items", posts);
			mav.addObject("itemsNotEmpty", true);
		}
		
		else
			mav.addObject("itemsNotEmpty", false);
		
		mav.setViewName("items/blog/postlist.html");
		return mav;
	}
	
	//Update post
	@GetMapping("/blog/update/{postId}")
	public ModelAndView blogPostUpdate(@ModelAttribute("postVO") PostVO postVO, @PathVariable("postId") String postId, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> postMap = new HashMap<>();
		postMap.put("postId", postId);
		PostDomain post = postService.getSinglePost(postMap);
		
		//HttpSession hs = req.getSession();
		Map<String, String> mbMap = new HashMap<>();
		mbMap.put("mbCode", post.getPostAuthorId());
		LoginDomain mb = mbService.getCode(mbMap);
		
		postVO.setId(post.getPostId());
		postVO.setTitle(post.getPostTitle());
		postVO.setSubtitle(post.getPostSubtitle());
		postVO.setContent(post.getPostContent());
		postVO.setTags(post.getPostTags());
		postVO.setMimg(post.getPostMimg());
		postVO.setAimg(mb.getMbImage());
		
		mav.addObject("postVO", postVO);
		mav.setViewName("items/blog/update.html");
		return mav;
	}
	
	@PostMapping("/blog/update/postUpdate")
	public ModelAndView postUpdateCommit(PostVO postVO) {
		ModelAndView mav = new ModelAndView();
		
		PostDomain post = PostDomain.builder()
				.postId(postVO.getId())
				.postTitle(postVO.getTitle())
				.postSubtitle(postVO.getSubtitle())
				.postContent(postVO.getContent())
				.postTags(postVO.getTags())
				.postAuthorimg(postVO.getAimg())
				.postMimg(postVO.getMimg())
				.build();
		
		postService.updatePost(post);
		
		String postId = postVO.getId();
		String path = "/blog/post/" + postId;
		mav.setViewName("redirect:" + path);
		return mav;
	}
	
	//Delete post
	@GetMapping("/blog/delete/{postId}")
	public ModelAndView blogPostDelete(@PathVariable("postId") String postId) {
		ModelAndView mav = new ModelAndView();
		
		Map<String, String> postMap = new HashMap<>();
		postMap.put("postId", postId);
		
		postService.deletePost(postMap);
		
		mav.addObject("data", new Message("게시글이 삭제되었습니다.", "/blog/home"));
		mav.setViewName("/static/Message");
		
		return mav;
	}
	
	//Make post
	@GetMapping("/blog/create")
	public ModelAndView blogPostCreate(PostVO postVO) {
		ModelAndView mav = new ModelAndView();
		
		postVO.setTitle("");
		postVO.setSubtitle("");
		postVO.setContent("");
		postVO.setTags("");
		postVO.setMimg("");
		
		mav.addObject("postVO", postVO);
		mav.setViewName("items/blog/create.html");
		return mav;
	}
	
	@PostMapping("/blog/postCreate")
	public ModelAndView postCreate(@ModelAttribute("postVO") PostVO postVO, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		
		//Get Mbinfo
		HttpSession hs = req.getSession();
		Map<String, String> map = new HashMap<>();
		map.put("mbCode", Integer.toString((Integer)hs.getAttribute("mbCode")));
		LoginDomain mb = mbService.getCode(map);
		
		//Now Time
		LocalDate now = LocalDate.now();
		DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String ntime = now.format(timeformat);
		
		PostDomain post = PostDomain.builder()
				.postAuthor(mb.getMbName())
				.postAuthorId(mb.getMbCode())
				.postTitle(postVO.getTitle())
				.postSubtitle(postVO.getSubtitle())
				.postContent(postVO.getContent())
				.postTags(postVO.getTags())
				.postAuthorimg(mb.getMbImage())
				.postMimg(postVO.getMimg())
				.postDate(ntime)
				.build();
		
		postService.createPost(post);
		
		mav.setViewName("redirect:/blog/home");
		return mav;
	}
	
	//Redirect post location
	@GetMapping("/blog/post")
	public ModelAndView postRedirect() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/blog/post/1");
		return mav;
	}

	//Return errorPost
	private PostDomain returnErrPost() {
		PostDomain post = PostDomain.builder()
				.postId("0")
				.postAuthor("Error")
				.postTitle("Post Not Found!!")
				.postContent("Content Not Found")
				.postDate("Date Not Found")
				.postTags("Tag ERROR")
				.build();
		
		return post;
	}
}