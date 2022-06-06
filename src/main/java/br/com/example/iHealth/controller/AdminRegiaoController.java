package br.com.example.iHealth.controller;

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

import br.com.example.iHealth.dto.RegiaoForm;
import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.PostoRepository;
import br.com.example.iHealth.repository.RegiaoRepository;
import br.com.example.iHealth.repository.MedicineRepository;
import br.com.example.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminRegiaoController {

	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostoRepository postoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "regioes")
	public String medications(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Regiao> regioes = regiaoRepository.findAll(Sort.by("id").ascending());
		model.addAttribute("regioes", regioes);

		if (regioes == null || regioes.isEmpty()) {
			return "admin/regiao/listavazia";
		} else {
			model.addAttribute("regioes", regioes);
			return "admin/regiao/regioes";
		}
	}
	
	@GetMapping(value = "cadastroRegiaoForm")
	public String regiaoForm(RegiaoForm regiaoForm,Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/regiao/regiaoForm";
	}
	
	@PostMapping(value = "cadastrarRegiao")
	public String cadastrarMedicamento(@Valid RegiaoForm regiaoForm, BindingResult result,Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		if (result.hasErrors()) {
			return "admin/regiao/regiaoForm";
		} else {
			Regiao regiao = regiaoForm.converter();
			regiaoRepository.save(regiao);
			return "redirect:/admin/regiaoSuccess";
		}
	}
	
	@GetMapping(value = "regiaoSuccess")
	public String requestSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/regiao/regiaoSuccess";
	}
	
	@GetMapping(value = "deletarRegiao/{id}")
	public String medicationDeletar(@PathVariable("id") Long id, Model model, Principal principal) {
		Optional<Regiao> regiao = regiaoRepository.findById(id);

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		if (regiao.isPresent()) {
			regiaoRepository.deleteById(id);
			return "redirect:/admin/regioes";
		} else {
			return "admin/regioes";
		}
	}
	
	@GetMapping(value = "editarRegiao/{id}")
	public String medicationFormEdit(@PathVariable("id") Long id, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Regiao regiao = regiaoRepository.findById(id).get();

		model.addAttribute("regiao", regiao);

		return "admin/regiao/regiaoFormEdit.html";
	}
	
	@PostMapping(value = "atualizarRegiao")
	public String recadastrar(Regiao regiao, BindingResult result) {

		if (regiao.getname() == null || regiao.getname().isEmpty()) {
			return "admin/regiao/regiaoFormEdit.html";
		} else {
			regiaoRepository.save(regiao);
			return "redirect:/admin/regioes";
		}

	}
}
