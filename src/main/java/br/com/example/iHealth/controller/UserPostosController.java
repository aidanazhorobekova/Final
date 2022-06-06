package br.com.example.iHealth.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.iHealth.model.Posto;
import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.model.Medicine;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.PostoRepository;
import br.com.example.iHealth.repository.RegiaoRepository;
import br.com.example.iHealth.repository.MedicineRepository;
import br.com.example.iHealth.repository.UserRepository;
import br.com.example.iHealth.service.PostoService;

@Controller
@RequestMapping(value = "user")
public class UserPostosController {

	@Autowired
	private RegiaoRepository regiaoRepository;
	
	@Autowired
	private MedicineRepository medicineRepository;
	
	@Autowired
	private PostoRepository postoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostoService postoService;
	
//	@GetMapping(value = "postos")
//	public String listaDePostos(Model model, Principal principal) {
//		
//		String username = principal.getName();
//		model.addAttribute("username", username);
//		
//		User user = userRepository.findByCpf(username);
//		model.addAttribute("user", user);
//		
//		Regiao regiao = user.getRegiao();
//		
//		List<Posto> postos = postoRepository.findAllByRegiao(regiao, Sort.by("id").ascending());
//		model.addAttribute("postos", postos);
//		
//		return "user/posto/postos";
//	}
	
	@GetMapping(value = "postos")
	public String getAllPages(Model model,Principal principal) {
		return umaPagina(model, principal,  1);
	}
	
	
	@GetMapping(value = "postos/{pageNumber}")
	public String umaPagina(Model model, Principal principal, @PathVariable("pageNumber") int currentPage) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Regiao regiao = user.getRegiao();
		
		Page<Posto> page = postoService.findPage(model, principal, currentPage);
		
		int totalPages =  page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Posto> postos = page.getContent();
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("postos", postos);
		
		return "user/posto/postos";
	}
	
	@GetMapping(value = "listaDeMedicines/{id}")
	@Cacheable(value = "listaDeMedicines")
	public ModelAndView listaDeMedicines(@PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Posto posto = postoRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("user/posto/listaDeMedicinesPosto");
		mv.addObject("posto", posto);
		
		List<Medicine> postoMedicines = posto.getMedicines();
		mv.addObject("postoMedicines", postoMedicines);
		return mv;
	}
	
	@GetMapping(value = "imagemPosto/{id}")
	@ResponseBody
	public byte[] exibirImagemd(@PathVariable("id") Long id) {
		Posto posto = this.postoRepository.getById(id);
		return posto.getUrlImagemPosto();
	}

}
