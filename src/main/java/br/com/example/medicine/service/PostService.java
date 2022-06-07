package br.com.example.medicine.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.com.example.medicine.model.Post;
import br.com.example.medicine.model.Region;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.PostRepository;
import br.com.example.medicine.repository.UserRepository;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Page<Post> findPage(Model model, Principal principal, int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 3);
		
		String username = principal.getName();
		
		User user = userRepository.findByCpf(username);

		Region region = user.getRegion();
		
		return postRepository.findAllByRegion(region, pageable);
	}
}
