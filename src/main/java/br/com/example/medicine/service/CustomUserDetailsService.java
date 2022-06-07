package br.com.example.medicine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.example.medicine.model.CustomUserDetails;
import br.com.example.medicine.model.User;
import br.com.example.medicine.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		
		User user = userRepository.findByCpf(cpf);
		
		if(user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new CustomUserDetails(user);
		
	}

}
