package br.com.example.medicine.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.RequestRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.RoleRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminUsersController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RequestRepository requestRepository;
	
	@GetMapping("users")
	public String usersLista(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "admin/users/listaUsuarios";
	}

	
}
