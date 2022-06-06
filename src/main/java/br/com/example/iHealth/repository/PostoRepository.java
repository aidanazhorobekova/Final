package br.com.example.iHealth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.iHealth.model.Posto;
import br.com.example.iHealth.model.Regiao;

@Repository
public interface PostoRepository extends JpaRepository<Posto, Long>{

	Posto findByname(String regiao);

	Page<Posto> findAllByRegiao(Regiao regiao, Pageable pageable);
}
