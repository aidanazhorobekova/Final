package br.com.example.medicine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.medicine.model.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>{

}
