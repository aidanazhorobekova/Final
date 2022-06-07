package br.com.example.medicine.controller;

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

import br.com.example.medicine.dto.UserForm;
import br.com.example.medicine.model.Region;
import br.com.example.medicine.model.Role;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.RoleRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping(value = "user/login")
public class LoginController {

	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping
	public String login() {
		 return "login/login";
	}
	
	@GetMapping("registrationForm")
	public String registrationForm(UserForm userForm, Model model) {
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		 return "login/registration";
	}
	
	@PostMapping("registration")
	public String registration(@Valid UserForm userForm, BindingResult result, Model model) {
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		if(result.hasErrors()) {
			return "login/registration";
		}
		 
		User user = userForm.converter(regionRepository);
		
		String passwordNaoCriptografada = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = passwordNaoCriptografada;
		String encodedPassword = encoder.encode(password);
		user.setPassword(encodedPassword);
			
		Role adminRole = roleRepository.findByName("USER");
		user.setRoles(Arrays.asList(adminRole));
	
			
		userRepository.save(user);
		return "login/registrationSuccess.html";
	}

}
	

