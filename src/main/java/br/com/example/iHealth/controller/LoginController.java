package br.com.example.iHealth.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.example.iHealth.dto.UserForm;
import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.model.Role;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.RegiaoRepository;
import br.com.example.iHealth.repository.RoleRepository;
import br.com.example.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "user/login")
public class LoginController {

	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping
	public String login() {
		 return "login/login";
	}
	
	@GetMapping("registationForm")
	public String registationForm(UserForm userForm, Model model) {
		
		List<Regiao> regioes = regiaoRepository.findAll();
		model.addAttribute("regioes", regioes);
		
		 return "login/registration";
	}
	
	@PostMapping("registation")
	public String registation(@Valid UserForm userForm, BindingResult result, Model model) {
		
		List<Regiao> regioes = regiaoRepository.findAll();
		model.addAttribute("regioes", regioes);
		
		if(result.hasErrors()) {
			return "login/registration";
		}
		 
		User user = userForm.converter(regiaoRepository);
		
		String senhaNaoCriptografada = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = senhaNaoCriptografada;
		String encodedPassword = encoder.encode(password);
		user.setPassword(encodedPassword);
			
		Role adminRole = roleRepository.findByName("USER");
		user.setRoles(Arrays.asList(adminRole));
	
			
		userRepository.save(user);
		return "login/registrationSuccess.html";
	}

}
	

