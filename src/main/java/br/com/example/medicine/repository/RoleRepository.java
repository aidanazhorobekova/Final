package br.com.example.medicine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.medicine.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);

}
