package br.com.example.medicine.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.example.medicine.dto.RegionForm;
import br.com.example.medicine.model.Region;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.PostRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.MedicineRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminRegionController {

	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "regions")
	public String medications(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Region> regions = regionRepository.findAll(Sort.by("id").ascending());
		model.addAttribute("regions", regions);

		if (regions == null || regions.isEmpty()) {
			return "admin/region/listavazia";
		} else {
			model.addAttribute("regions", regions);
			return "admin/region/regions";
		}
	}
	
	@GetMapping(value = "cadastroRegionForm")
	public String regionForm(RegionForm regionForm,Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/region/regionForm";
	}
	
	@PostMapping(value = "cadastrarRegion")
	public String cadastrarMedication(@Valid RegionForm regionForm, BindingResult result,Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		if (result.hasErrors()) {
			return "admin/region/regionForm";
		} else {
			Region region = regionForm.converter();
			regionRepository.save(region);
			return "redirect:/admin/regionSuccess";
		}
	}
	
	@GetMapping(value = "regionSuccess")
	public String requestSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/region/regionSuccess";
	}
	
	@GetMapping(value = "deleteRegion/{id}")
	public String medicationDelete(@PathVariable("id") Long id, Model model, Principal principal) {
		Optional<Region> region = regionRepository.findById(id);

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		if (region.isPresent()) {
			regionRepository.deleteById(id);
			return "redirect:/admin/regions";
		} else {
			return "admin/regions";
		}
	}
	
	@GetMapping(value = "editRegion/{id}")
	public String medicationFormEdit(@PathVariable("id") Long id, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Region region = regionRepository.findById(id).get();

		model.addAttribute("region", region);

		return "admin/region/regionFormEdit.html";
	}
	
	@PostMapping(value = "updateRegion")
	public String recadastrar(Region region, BindingResult result) {

		if (region.getname() == null || region.getname().isEmpty()) {
			return "admin/region/regionFormEdit.html";
		} else {
			regionRepository.save(region);
			return "redirect:/admin/regions";
		}

	}
}
