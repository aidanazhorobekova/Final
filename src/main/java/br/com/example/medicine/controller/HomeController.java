package br.com.example.medicine.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.RequestRepository;
import br.com.example.medicine.repository.PostRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.MedicineRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping(value = "home")
public class HomeController {
	
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
	public String home(Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "home";
	}
}
