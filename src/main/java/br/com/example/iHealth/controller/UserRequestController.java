package br.com.example.iHealth.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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

import br.com.example.iHealth.model.Request;
import br.com.example.iHealth.model.Medicine;
import br.com.example.iHealth.model.Status;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.RequestRepository;
import br.com.example.iHealth.repository.PostoRepository;
import br.com.example.iHealth.repository.RegiaoRepository;
import br.com.example.iHealth.repository.MedicineRepository;
import br.com.example.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "user")
public class UserRequestController {

	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostoRepository postoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping(value = "gerarRequest")
	public String cadastrarRequest(Request request,Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		request.setUser(user);
		requestRepository.save(request);
		return "user/request/gerarRequest";
	
	}
	
	@GetMapping(value = "request/{id}")
	public ModelAndView listaDeMedicines(@PathVariable(name = "id") Long id, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Request request = requestRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("user/request/preencherRequest");
		mv.addObject("request", request);
		
		List<Medicine> medicinesNaoAssociados  = medicineRepository.findAll();
		medicinesNaoAssociados.removeAll(request.getMedicines());
		mv.addObject("medicines", medicinesNaoAssociados);
		
		List<Medicine> requestMedicines = request.getMedicines();
		mv.addObject("requestMedicines", requestMedicines);
		return mv;
	}
	
	@PostMapping("/associarMedicineRequest")
	public String associarMedicine(@ModelAttribute Medicine medicine, @RequestParam Long idRequest, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Request request = requestRepository.findById(idRequest).get();
		medicine  = medicineRepository.findById(medicine.getId()).get();
		
		request.getMedicines().add(medicine);
		requestRepository.save(request);
		
		return "redirect:/user/request/" + idRequest;
	}
	
	@PostMapping(value = "salvarRequest/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String salvarRequest(@RequestParam("anexoReceita") MultipartFile file, @PathVariable(name = "id") Long id, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Request request = requestRepository.getById(id);
		try {
			request.setReceitaMedica(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		requestRepository.save(request);
		return "redirect:/user/requestSuccess";
	
	}
	
	@GetMapping(value = "requestSuccess")
	public String requestSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/request/requestSuccess";
	}
	
	@GetMapping(value = "requests")
	public String listaDeRequests(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByUser(user, Sort.by("id").descending());
		
		String url = "todos";
		model.addAttribute("url", url);
		
		model.addAttribute("requests", requests);
		
		if(requests == null || requests.size() == 0) {
			return "user/request/requestVoids";
		} else {
			return "user/request/requestList";
		}
	}
	
	@GetMapping(value = "requests/recusados")
	public String listaDeRequestsRecusados(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByUserAndStatus(user, Status.RECUSADO, Sort.by("id").descending());
		String url = "recusados";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		
		if(requests == null || requests.size() == 0) {
			return "user/request/requestVoids";
		} else {
			return "user/request/requestList";
		}
	}
	
	@GetMapping(value = "requests/aceitos")
	public String listaDeRequestsAceitos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByUserAndStatus(user, Status.ACEITO, Sort.by("id").descending());
		String url = "aceitos";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		
		if(requests == null || requests.size() == 0) {
			return "user/request/requestVoids";
		} else {
			return "user/request/requestList";
		}
	}
	
	@GetMapping(value = "requests/entregues")
	public String listaDeRequestsEntregues(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByUserAndStatus(user, Status.ENTREGUE, Sort.by("id").descending());
		String url = "entregues";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		
		if(requests == null || requests.size() == 0) {
			return "user/request/requestVoids";
		} else {
			return "user/request/requestList";
		}
	}
	
	@GetMapping(value = "requests/analise")
	public String listaDeRequestsAnalise(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Request> requests = requestRepository.findAllByUserAndStatus(user, Status.ANALISE, Sort.by("id").descending());
		String url = "analise";
		model.addAttribute("url", url);
		model.addAttribute("requests", requests);
		
		if(requests == null || requests.size() == 0) {
			return "user/request/requestVoids";
		} else {
			return "user/request/requestList";
		}
	}
	
	@GetMapping(value = "excluirRequest/{id}")
	public String deletarRequest(@PathVariable("id") Long id) {
		
		requestRepository.deleteById(id);
		return "redirect:/user/requests";
	}
	

	@GetMapping(value = "cancelarRequest/{id}")
	public String cancelarRequest(@PathVariable("id") Long id) {
		
		requestRepository.deleteById(id);
		return "redirect:/home";
	}
	
	@GetMapping(value = "detalheRequest/{id}")
	public ModelAndView detalheMedicine(@PathVariable(name = "id") Long id, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Request request = requestRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("user/request/requestDetalhes");
		mv.addObject("request", request);
		
		List<Medicine> medicinesNaoAssociados  = medicineRepository.findAll();
		medicinesNaoAssociados.removeAll(request.getMedicines());
		mv.addObject("medicines", medicinesNaoAssociados);
		
		List<Medicine> requestMedicines = request.getMedicines();
		mv.addObject("requestMedicines", requestMedicines);
		return mv;
	}
	
	@GetMapping(value = "imagem/{id}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id") Long id) {
		Request request = this.requestRepository.getById(id);
		return request.getReceitaMedica();
	}
	

	@GetMapping(value = "/medicine/imagem/{id}")
	@ResponseBody
	public byte[] exibirImagemMedicine(@PathVariable("id") Long id) {
		Medicine medicine = this.medicineRepository.getById(id);
		return medicine.getImagem();
	}
	
}


