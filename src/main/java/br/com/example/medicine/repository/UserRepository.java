package br.com.example.medicine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.medicine.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByCpf(String cpf);

	User getUserByCpf(String username);

	
}
