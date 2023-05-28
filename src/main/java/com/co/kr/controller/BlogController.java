package com.co.kr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.domain.PostDomain;
import com.co.kr.service.PostService;

@Controller
@RequestMapping(value = "/")
public class BlogController {
	@Autowired
	private PostService postService;
	
	@GetMapping("/blog/home")
	public ModelAndView bloghome() {
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
	public ModelAndView blogpost(@PathVariable("postId") String postId) {
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
