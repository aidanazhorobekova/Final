package br.com.example.iHealth.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.com.example.iHealth.model.Posto;
import br.com.example.iHealth.model.Regiao;
import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.PostoRepository;
import br.com.example.iHealth.repository.UserRepository;

@Service
public class PostoService {
	
	@Autowired
	PostoRepository postoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Page<Posto> findPage(Model model, Principal principal, int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 3);
		
		String username = principal.getName();
		
		User user = userRepository.findByCpf(username);

		Regiao regiao = user.getRegiao();
		
		return postoRepository.findAllByRegiao(regiao, pageable);
	}
}
