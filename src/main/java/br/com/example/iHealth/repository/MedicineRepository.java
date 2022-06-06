package br.com.example.iHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.iHealth.model.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>{

}
