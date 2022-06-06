package br.com.example.iHealth.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.iHealth.model.Posto;
import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.model.Medicine;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.PostoRepository;
import br.com.example.iHealth.repository.RegiaoRepository;
import br.com.example.iHealth.repository.MedicineRepository;
import br.com.example.iHealth.repository.UserRepository;

@Controller
@RequestMapping("admin")
public class AdminPostosController {

	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostoRepository postoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "postos")
	public String listaDePostos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Posto> postos = postoRepository.findAll(Sort.by("id").ascending());
		model.addAttribute("postos", postos);
		
		if(postos.isEmpty() || postos == null) {
			return "admin/posto/postosVazios";
		}
		
		return "admin/posto/postos";
	}
	
	@GetMapping(value = "postoForm")
	public String PostoForm(Posto posto, Model model, Principal principal) {
		List<Regiao> regioes = regiaoRepository.findAll();
		model.addAttribute("regioes", regioes);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		
		return "admin/posto/postoForm";
	}
	
	@PostMapping(value = "cadastrarPosto",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String cadastrarPosto(Posto posto,  @RequestParam("imagemFile") MultipartFile file, @RequestParam("regiaoFile") String regiao, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		try {
			Regiao r = regiaoRepository.findByname(regiao);
			posto.setRegiao(r);
			posto.setUrlImagemPosto(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		postoRepository.save(posto);
		return "redirect:/admin/postoSuccess";
	}
	
	@GetMapping(value = "postoSuccess")
	public String requestSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/posto/postoSuccess";
	}
	
	
	@GetMapping(value = "deletarPosto/{id}")
	public String postoDeletar(@PathVariable("id") Long id) {
		Optional<Posto> posto = postoRepository.findById(id);

		if (posto.isPresent()) {
			postoRepository.deleteById(id);
			return "redirect:/admin/postos";
		} else {
			return "admin/postos";
		}
	}
	
	@GetMapping(value = "editarPosto/{id}")
	public String postoFormEdit(@PathVariable("id") Long id, Model model, Principal principal) {

		Posto posto = postoRepository.findById(id).get();
		model.addAttribute("posto", posto);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Regiao> regioes = regiaoRepository.findAll();
		
		model.addAttribute("regioes", regioes);

		return "admin/posto/postoEditForm.html";
	}
	
	@PostMapping(value = "atualizarPosto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String recadastrarPosto(Posto posto,@RequestParam("imagemFile") MultipartFile file, @RequestParam("regiaoFile") String regiao) {
		try {
			Regiao r = regiaoRepository.findByname(regiao);
			posto.setRegiao(r);
			posto.setUrlImagemPosto(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		postoRepository.save(posto);
		return "redirect:/admin/postos";
	}

	
	
	@GetMapping(value = "listaDeMedicines/{id}")
	public ModelAndView listaDeMedicines(@PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Posto posto = postoRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("admin/posto/listaDeMedicinesPosto");
		mv.addObject("posto", posto);
		
		List<Medicine> medicinesNaoAssociados  = medicineRepository.findAll();
		medicinesNaoAssociados.removeAll(posto.getMedicines());
		mv.addObject("medicines", medicinesNaoAssociados);
		
		List<Medicine> postoMedicines = posto.getMedicines();
		mv.addObject("postoMedicines", postoMedicines);
		return mv;
	}
	
	@PostMapping( value = "associarMedicinePosto")
	public String associarMedicines(@ModelAttribute Medicine medicine, @RequestParam Long idPosto) {
		
		Posto posto = postoRepository.findById(idPosto).get();
		medicine  = medicineRepository.findById(medicine.getId()).get();
		
		posto.getMedicines().add(medicine);
		postoRepository.save(posto);
		
		return "redirect:/admin/listaDeMedicines/" + idPosto;
	}
	
	@GetMapping(value = "imagemPosto/{id}")
	@ResponseBody
	public byte[] exibirImagemd(@PathVariable("id") Long id) {
		Posto posto = this.postoRepository.getById(id);
		return posto.getUrlImagemPosto();
	}
}
