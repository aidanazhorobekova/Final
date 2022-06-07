package br.com.example.medicine.controller;

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

import br.com.example.medicine.model.Region;
import br.com.example.medicine.model.Role;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.RequestRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.RoleRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RequestRepository requestRepository;
	
	@GetMapping("details")
	public String usersDetails(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		return "user/details/details";
	}
	
	@GetMapping("edit")
	public String userEdit(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		return "user/details/edit";
	}
	
	@GetMapping(value = "editPassword")
	public String passwordEdit(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		return "user/details/editPassword";
	}
	
	@PostMapping(value = "update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String update(User user, Model model, @RequestParam("regionField") String region,  Principal principal) {
	
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User userLogged = userRepository.findByCpf(username);
		
		Collection<Role> roles = userLogged.getRoles();
		user.setRoles(roles);
		
		user.setPassword(userLogged.getPassword());
			
		Region r = regionRepository.findByname(region);
		user.setRegion(r);
		
		user.setPhoto(userLogged.getPhoto());
			
		userRepository.save(user);
		return "user/details/editSuccess.html";
	}
	
	@PostMapping("updatePassword")
	public String updatePassword(User user, Model model, @RequestParam("passwordField") String password1, Principal principal) {
	
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User userLogged = userRepository.findByCpf(username);
		
		String passwordNaoCriptografada = password1;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = passwordNaoCriptografada;
		String encodedPassword = encoder.encode(password);
		userLogged.setPassword(encodedPassword);
			
		userRepository.save(userLogged);
		return "user/details/editSuccess.html";
	}
	
	@GetMapping(value = "exclude/{id}")
	public String deleteRequest(@PathVariable("id") Long id) {
		
		User user = userRepository.findById(id).get();
		
		user.setRoles(null);

		userRepository.deleteById(id);
		return "redirect:/admin/users";
	}
	
	@GetMapping(value = "image/{id}")
	@ResponseBody
	public byte[] displayImage(@PathVariable("id") Long id) {
		User user = this.userRepository.getById(id);
		return user.getPhoto();
	}
	
	@GetMapping(value = "editPhoto")
	public String editPhoto(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/details/editPhoto";
	}
	
	@PostMapping("updatePhoto")
	public String updatePhoto(User user, Model model, @RequestParam("photoFile") MultipartFile photo, Principal principal) throws IOException {
	
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User userLogged = userRepository.findByCpf(username);
		
		userLogged.setPhoto(photo.getBytes());
			
		userRepository.save(userLogged);
		return "user/details/editSuccess.html";
	}
}
