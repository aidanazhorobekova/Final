package br.com.example.medicine.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.example.medicine.dto.RequestNewComment;
import br.com.example.medicine.model.Comment;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.CommentRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping("comment")
public class CommentController {

	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("homeComments")
	public String homeComments(RequestNewComment request, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Comment> comments = commentRepository.findAll(Sort.by("id").descending());
		model.addAttribute("comments", comments);
		
		String url = "comments";
		 model.addAttribute("url", url);
		
		if(comments.isEmpty() || comments.size() == 0) {
			
			return "user/comment/homeVaziaComments";
		}
		return "user/comment/homeComments";
	}
	
	@GetMapping("listarTodos")
	public String listarTodosComments(RequestNewComment request, Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Comment> comments = commentRepository.findAll(Sort.by("id").descending());
		model.addAttribute("comments", comments);
		
		String url = "comments";
		 model.addAttribute("url", url);
		if(comments.isEmpty() || comments.size() == 0) {
			
			return "user/comment/homeVaziaComments";
		}
		return "user/comment/homeComments";
	}
	
	@GetMapping("listarPorUsuario")
	public String listarPorUsuario(RequestNewComment request, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		
		List<Comment> comments = commentRepository.findAllByUser(user,Sort.by("id").descending());
		model.addAttribute("comments", comments);
		
		String url = "commentsUsuario";
		 model.addAttribute("url", url);
		if(comments.isEmpty() || comments.size() == 0) {
			
			return "user/comment/homeVaziaCommentsUsuario";
		}
		return "user/comment/homeComments";
	}
	
	@GetMapping("exclude/{id}")
	public String excludeComment(Comment comment, BindingResult result) {
		
		commentRepository.deleteById(comment.getId());
		
		return "redirect:/comment/homeComments";
	}
	
	@GetMapping("formComment")
	public String formComment(RequestNewComment request, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/comment/commentForm";
	}
	
	@PostMapping("new")
	public String newComment(@Valid RequestNewComment request, BindingResult result, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user); 
		
		if(result.hasErrors()){
			return "user/comment/commentForm";
		}
		
		User user1 = userRepository.getUserByCpf(username);
		Comment comment = request.toComment();
		comment.setUser(user1);
		commentRepository.save(comment);
		
		model.addAttribute("user1", user1);
		return "redirect:/comment/commentSuccess";
	}
	
	@GetMapping(value = "commentSuccess")
	public String requestSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/comment/commentSuccess";
	}
	
	
}