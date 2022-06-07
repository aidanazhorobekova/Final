package br.com.example.medicine.controller;

import java.security.Principal;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import br.com.example.medicine.model.Request;
import br.com.example.medicine.model.Medicine;
import br.com.example.medicine.model.Status;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.RequestRepository;
import br.com.example.medicine.repository.PostRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.MedicineRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminRequestController {

	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping(value = "salvarRequest/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String salvarRequest(@RequestParam("status") Status status, @PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Request request = requestRepository.getById(id);
		request.setStatus(status);
		requestRepository.save(request);
		
		List<Request> requests = requestRepository.findAll(Sort.by("id").descending());
		String url = "todos";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		return "admin/request/requestList";
	
	}
	
	@GetMapping(value = "requestsAdmin")
	public String listaDeRequests(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAll(Sort.by("id").descending());
		String url = "todos";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		
		return "admin/request/requestList";
	}
	
	@GetMapping(value = "requestsAdmin/recusados")
	public String listaDeRequestsRecusados(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByStatus(Status.RECUSADO,Sort.by("id").descending());
		String url = "recusados";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		
		if(requests.isEmpty() || requests == null) {
			return "admin/request/requestVoids";
		}
		
		return "admin/request/requestList";
	}
	
	@GetMapping(value = "requestsAdmin/aceitos")
	public String listaDeRequestsAceitos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByStatus(Status.ACEITO, Sort.by("id").descending());
		String url = "aceitos";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		

		if(requests.isEmpty() || requests == null) {
			return "admin/request/requestVoids";
		}
		
		return "admin/request/requestList";
	}
	
	@GetMapping(value = "requestsAdmin/entregues")
	public String listaDeRequestsEntregues(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByStatus(Status.ENTREGUE, Sort.by("id").descending());
		String url = "entregues";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		

		if(requests.isEmpty() || requests == null) {
			return "admin/request/requestVoids";
		}
		
		return "admin/request/requestList";
	}
	
	@GetMapping(value = "requestsAdmin/analise")
	public String listaDeRequestsAnalise(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByStatus(Status.ANALISE, Sort.by("id").descending());
		String url = "analise";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		

		if(requests.isEmpty() || requests == null) {
			return "admin/request/requestVoids";
		}
		
		return "admin/request/requestList";
	}

	@GetMapping(value = "detalheRequest/{id}")
	public ModelAndView detalheMedicine(@PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Request request = requestRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("admin/request/orderRequest");
		mv.addObject("request", request);
		
		List<Medicine> medicinesNaoAssociados  = medicineRepository.findAll();
		medicinesNaoAssociados.removeAll(request.getMedicines());
		mv.addObject("medicines", medicinesNaoAssociados);
		
		List<Medicine> requestMedicines = request.getMedicines();
		mv.addObject("requestMedicines", requestMedicines);
		return mv;
	}
	
	@GetMapping(value = "excludeRequest/{id}")
	public String deleteRequest(@PathVariable("id") Long id) {
		
		requestRepository.deleteById(id);
		return "redirect:/admin/requestsAdmin";
	}
}



