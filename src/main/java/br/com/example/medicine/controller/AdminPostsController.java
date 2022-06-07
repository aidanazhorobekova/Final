package br.com.example.medicine.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.medicine.model.Post;
import br.com.example.medicine.model.Region;
import br.com.example.medicine.model.Medicine;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.PostRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.MedicineRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping("admin")
public class AdminPostsController {

	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "posts")
	public String listaDeposts(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Post> posts = postRepository.findAll(Sort.by("id").ascending());
		model.addAttribute("posts", posts);
		
		if(posts.isEmpty() || posts == null) {
			return "admin/post/postsVazios";
		}
		
		return "admin/post/posts";
	}
	
	@GetMapping(value = "postForm")
	public String PostForm(Post post, Model model, Principal principal) {
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		
		return "admin/post/postForm";
	}
	
	@PostMapping(value = "cadastrarPost",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String cadastrarPost(Post post,  @RequestParam("imageFile") MultipartFile file, @RequestParam("regionFile") String region, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		try {
			Region r = regionRepository.findByname(region);
			post.setRegion(r);
			post.setUrlImagemPost(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		postRepository.save(post);
		return "redirect:/admin/postSuccess";
	}
	
	@GetMapping(value = "postSuccess")
	public String requestSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/post/postSuccess";
	}
	
	
	@GetMapping(value = "deletePost/{id}")
	public String postDelete(@PathVariable("id") Long id) {
		Optional<Post> post = postRepository.findById(id);

		if (post.isPresent()) {
			postRepository.deleteById(id);
			return "redirect:/admin/posts";
		} else {
			return "admin/posts";
		}
	}
	
	@GetMapping(value = "editPost/{id}")
	public String postFormEdit(@PathVariable("id") Long id, Model model, Principal principal) {

		Post post = postRepository.findById(id).get();
		model.addAttribute("post", post);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Region> regions = regionRepository.findAll();
		
		model.addAttribute("regions", regions);

		return "admin/post/postEditForm.html";
	}
	
	@PostMapping(value = "updatePost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String recadastrarPost(Post post,@RequestParam("imageFile") MultipartFile file, @RequestParam("regionFile") String region) {
		try {
			Region r = regionRepository.findByname(region);
			post.setRegion(r);
			post.setUrlImagemPost(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		postRepository.save(post);
		return "redirect:/admin/posts";
	}

	
	
	@GetMapping(value = "listaDeMedicines/{id}")
	public ModelAndView listaDeMedicines(@PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Post post = postRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("admin/post/listaDeMedicinesPost");
		mv.addObject("post", post);
		
		List<Medicine> medicinesNaoAssociados  = medicineRepository.findAll();
		medicinesNaoAssociados.removeAll(post.getMedicines());
		mv.addObject("medicines", medicinesNaoAssociados);
		
		List<Medicine> postMedicines = post.getMedicines();
		mv.addObject("postMedicines", postMedicines);
		return mv;
	}
	
	@PostMapping( value = "associarMedicinePost")
	public String associarMedicines(@ModelAttribute Medicine medicine, @RequestParam Long idPost) {
		
		Post post = postRepository.findById(idPost).get();
		medicine  = medicineRepository.findById(medicine.getId()).get();
		
		post.getMedicines().add(medicine);
		postRepository.save(post);
		
		return "redirect:/admin/listaDeMedicines/" + idPost;
	}
	
	@GetMapping(value = "imagePost/{id}")
	@ResponseBody
	public byte[] displayImaged(@PathVariable("id") Long id) {
		Post post = this.postRepository.getById(id);
		return post.getUrlImagemPost();
	}
}
