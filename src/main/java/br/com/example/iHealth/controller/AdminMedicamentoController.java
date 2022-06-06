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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.example.iHealth.model.Medicine;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.MedicineRepository;
import br.com.example.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminMedicamentoController {

	@Autowired
	MedicineRepository medicineRepository;

	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "medications")
	public String medications(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Medicine> medicines = medicineRepository.findAll(Sort.by("id").descending());
		model.addAttribute("medicines", medicines);

		if (medicines == null || medicines.isEmpty()) {
			return "admin/medication/medicationsvoids";
		} else {
			model.addAttribute("medicines", medicines);
			return "admin/medication/medications";
		}

	}

	@GetMapping(value = "cadastroMedicationForm")
	public String medicationForm(Medicine medicine, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/medication/medicationForm";
	}

	@PostMapping(value = "cadastrarMedicamento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String cadastrarMedicamento(Medicine medicine, @RequestParam("imagemFile") MultipartFile file, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		try {
				medicine.setImagem(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			medicineRepository.save(medicine);
			return "redirect:/admin/medicationSuccess";
	}
	
	@GetMapping(value = "medicationSuccess")
	public String requestSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/medication/medicationSuccess";
	}
	
	@GetMapping(value = "imagem/{id}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id") Long id) {
		Medicine medicine = this.medicineRepository.getById(id);
		return medicine.getImagem();
	}
	
	@GetMapping(value = "deletarMedicamento/{id}")
	public String medicationDeletar(@PathVariable("id") Long id) {
		Optional<Medicine> medicine = medicineRepository.findById(id);

		if (medicine.isPresent()) {
			medicineRepository.deleteById(id);
			return "redirect:/admin/medications";
		} else {
			return "admin/medications";
		}
	}

	@GetMapping(value = "editarMedicamento/{id}")
	public String medicationFormEdit(@PathVariable("id") Long id, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Medicine medication = medicineRepository.findById(id).get();

		model.addAttribute("medication", medication);

		return "admin/medication/medicationFormEdit.html";
	}

	@PostMapping(value = "atualizarMedicamento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String recadastrar(Medicine medicine, @RequestParam("imagemFile") MultipartFile file) {
		try {
			medicine.setImagem(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		medicineRepository.save(medicine);
		return "redirect:/admin/medications";

	}
}
