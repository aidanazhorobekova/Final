package br.com.example.iHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.iHealth.model.Regiao;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long>{

	Regiao findByname(String nameRegiao);

}
