package br.com.example.iHealth.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.model.Role;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.RequestRepository;
import br.com.example.iHealth.repository.RegiaoRepository;
import br.com.example.iHealth.repository.RoleRepository;
import br.com.example.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminUsersController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RequestRepository requestRepository;
	
	@GetMapping("usuarios")
	public String usersLista(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<User> usuarios = userRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		return "admin/usuarios/listaUsuarios";
	}

	
}
