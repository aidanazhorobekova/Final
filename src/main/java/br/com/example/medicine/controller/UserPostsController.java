package br.com.example.medicine.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.medicine.model.Post;
import br.com.example.medicine.model.Region;
import br.com.example.medicine.model.Medicine;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.PostRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.MedicineRepository;
import br.com.example.medicine.repository.UserRepository;
import br.com.example.medicine.service.PostService;

@Controller
@RequestMapping(value = "user")
public class UserPostsController {

	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private MedicineRepository medicineRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostService postService;
	
//	@GetMapping(value = "posts")
//	public String listaDeposts(Model model, Principal principal) {
//		
//		String username = principal.getName();
//		model.addAttribute("username", username);
//		
//		User user = userRepository.findByCpf(username);
//		model.addAttribute("user", user);
//		
//		Region region = user.getRegion();
//		
//		List<Post> posts = postRepository.findAllByRegion(region, Sort.by("id").ascending());
//		model.addAttribute("posts", posts);
//		
//		return "user/post/posts";
//	}
	
	@GetMapping(value = "posts")
	public String getAllPages(Model model,Principal principal) {
		return umaPagina(model, principal,  1);
	}
	
	
	@GetMapping(value = "posts/{pageNumber}")
	public String umaPagina(Model model, Principal principal, @PathVariable("pageNumber") int currentPage) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Region region = user.getRegion();
		
		Page<Post> page = postService.findPage(model, principal, currentPage);
		
		int totalPages =  page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Post> posts = page.getContent();
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("posts", posts);
		
		return "user/post/posts";
	}
	
	@GetMapping(value = "listaDeMedicines/{id}")
	@Cacheable(value = "listaDeMedicines")
	public ModelAndView listaDeMedicines(@PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Post post = postRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("user/post/listaDeMedicinesPost");
		mv.addObject("post", post);
		
		List<Medicine> postMedicines = post.getMedicines();
		mv.addObject("postMedicines", postMedicines);
		return mv;
	}
	
	@GetMapping(value = "imagePost/{id}")
	@ResponseBody
	public byte[] displayImaged(@PathVariable("id") Long id) {
		Post post = this.postRepository.getById(id);
		return post.getUrlImagemPost();
	}

}
